import service.UserService;

public class Demo02 {
    public static void main(String[] args) {
        goshow(new UserService() {
            @Override
            public void show() {
                System.out.println("111");
            }
        });
        goshow(()-> System.out.println("222"));
    }
    public static void goshow(UserService userService){
        userService.show();
    }
}
