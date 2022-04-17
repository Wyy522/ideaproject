public class FIFOCache {

    //按照访问时间排序,保存所有key-value  
    private final Map<String,Value> CACHE = new LinkedHashMap<>();

    //过期数据，只保存有过期时间的key  
    //暂不考虑并发，我们认为同一个时间内没有重复的key，如果改造的话，可以将value换成set  
    private final TreeMap<Long, String> EXPIRED = new TreeMap<>();

    private final int capacity;

    public FIFOCache(int capacity) {
        this.capacity = capacity;
    }

    public Object get(String key) {
        //  
        Value value = CACHE.get(key);
        if (value == null) {
            return null;
        }

        //如果不包含过期时间  
        long expired = value.expired;
        long now = System.nanoTime();
        //已过期  
        if (expired > 0 && expired <= now) {
            CACHE.remove(key);
            EXPIRED.remove(expired);
            return null;
        }
        return value.value;
    }

    public void put(String key,Object value) {
        put(key,value,-1);
    }


    public void put(String key,Object value,int seconds) {
        //如果容量不足，移除过期数据  
        if (capacity < CACHE.size()) {
            long now = System.nanoTime();
            //有过期的,全部移除  
            Iterator<Long> iterator = EXPIRED.keySet().iterator();
            while (iterator.hasNext()) {
                long _key = iterator.next();
                //如果已过期，或者容量仍然溢出，则删除  
                if (_key > now) {
                    break;
                }
                //一次移除所有过期key  
                String _value = EXPIRED.get(_key);
                CACHE.remove(_value);
                iterator.remove();
            }
        }

        //如果仍然容量不足，则移除最早访问的数据  
        if (capacity < CACHE.size()) {
            Iterator<String> iterator = CACHE.keySet().iterator();
            while (iterator.hasNext() && capacity < CACHE.size()) {
                String _key = iterator.next();
                Value _value = CACHE.get(_key);
                long expired = _value.expired;
                if (expired > 0) {
                    EXPIRED.remove(expired);
                }
                iterator.remove();
            }
        }

        //如果此key已存在,移除旧数据  
        Value current = CACHE.remove(key);
        if (current != null && current.expired > 0) {
            EXPIRED.remove(current.expired);
        }
        //如果指定了过期时间  
        if(seconds > 0) {
            long expireTime = expiredTime(seconds);
            EXPIRED.put(expireTime,key);
            CACHE.put(key,new Value(expireTime,value));
        } else {
            CACHE.put(key,new Value(-1,value));
        }

    }

    private long expiredTime(int expired) {
        return System.nanoTime() + TimeUnit.SECONDS.toNanos(expired);
    }

    public void remove(String key) {
        Value value = CACHE.remove(key);
        if(value == null) {
            return;
        }
        long expired = value.expired;
        if (expired > 0) {
            EXPIRED.remove(expired);
        }
    }


    class Value {
        long expired; //过期时间,纳秒  
        Object value;
        Value(long expired,Object value) {
            this.expired = expired;
            this.value = value;
        }
    }
}  