package StudentSystem;

import java.util.ArrayList;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("欢迎来到学生管理系统登录界面");
            System.out.println("请选择操作：1.登录 2.注册 3.忘记密码 4.退出");
            int choose = sc.nextInt();
            switch (choose){
                case 1 -> login(list);
                case 2 -> register(list);
                case 3 -> forgetPassword(list);
                case 4 -> {
                    System.out.println("谢谢使用");
                    System.exit(0);
                }
                default-> System.out.println("没有此选项");
            }
        }
    }

    

    private static void register(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        String personID;
        String phoneNumber;
        //1.键盘录入用户名
        while (true) {
            System.out.println("请输入用户名：");
            username = sc.next();
            //用户名校验
            boolean flag1 = checkUsername(username);
            if(!flag1){
                System.out.println("用户名格式不正确，需重新输入");
                continue;
            }
            boolean flag2 = contains(list, username);
            if(flag2){
                System.out.println("用户名已存在，请重新输入");
//                continue;
            }else{
                System.out.println("用户名"+username+"可用");
                break;
            }
        }
        //2.键盘录入密码
        while (true) {
            System.out.println("请输入密码：");
            password = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();
            if(!password.equals(password2)){
                System.out.println("密码不一致，请重新输入");
                continue;
            }else{
                System.out.println("两次密码输入，继续录入其他信息");
                break;
            }
        }
        //3.键盘录入身份证号码
        while (true) {
            System.out.println("请输入身份证号码：");
            personID = sc.next();
            boolean flag3 = checkPersonID(personID);
            if(!flag3){
                System.out.println("身份证号码格式不正确，请重新输入：");
                continue;
            }else{
                System.out.println("身份证号码"+personID+"格式正确");
                break;
            }
        }
        //4.键盘录入手机号
        while (true) {
            System.out.println("请输入手机号：");
            phoneNumber = sc.next();
            boolean flag4 = checkPhoneNumber(phoneNumber);
            if(!flag4){
                System.out.println("手机号格式不正确，请重新输入：");
                continue;
            }else{
                System.out.println("手机号"+phoneNumber+"格式正确");
                break;
            }
        }
        //创建用户对象
        User u = new User(username, password, personID, phoneNumber);
        //将用户对象添加到list集合
        list.add(u);
        System.out.println("注册成功");
        printList(list);
    }
    private static void login(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入用户名：");
            String username = sc.next();
            boolean flag = contains(list, username);
            if(!flag){
                System.out.println("用户名"+username+"未注册，请先注册");
                return;
            }
            System.out.println("请输入密码：");
            String password = sc.next();
            //验证码验证逻辑
            while (true) {
                String rightCode = getCode();
                System.out.println("请输入验证码：");
                String code = sc.next();
                if(code.equalsIgnoreCase(rightCode)){
                    System.out.println("验证码正确");
                    break;
                }else {
                    System.out.println("验证码错误");
                    continue;
                }
            }
            // 判断用户名和密码是否正确
            // 封装思想:直接将所有到的成员变量封装成一个对象，作为方法的参数
            User userInfo = new User(username, password, null, null);
            boolean result = checkUserInfo(list, userInfo);
            if(result){
                return;
            }else{
                System.out.println("用户名或密码错误，你还剩"+(2-i)+"次机会");
            }
        }
    }
    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入当前用户名：");
        String username = sc.next();
        if(!contains(list, username)){
            System.out.println("当前用户名不存在，请先注册：");
            return;
        }else{
            System.out.println("当前用户名存在，输入身份证号码和手机号进行验证：");
        }
        //键入身份证号码和手机号
        System.out.println("请输入身份证号：");
        String personID = sc.next();
        System.out.println("请输入手机号：");
        String phoneNumber = sc.next();
        // 获取用户对象的索引
        int userIndex = findIndex(list, username);
        User user = list.get(userIndex);
        if(!(user.getPersonID().equals(personID) && user.getPhoneNumber().equals(phoneNumber))){
            System.out.println("身份证号码和手机号不匹配");
            return;
        }
        String newPassword;
        while (true) {
            System.out.println("请输入新密码：");
            newPassword = sc.next();
            System.out.println("请再次输入密码：");
            String newPassword2 = sc.next();
            if(!newPassword.equals(newPassword2)){
                System.out.println("两次密码输入不一致");
                continue;
            }else{
                break;
            }
        }
        //修改对应用户的密码
        user.setPassword(newPassword);
        System.out.println("密码修改成功");
    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    private static void printList(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
//            System.out.println("username"+"\t"+"password"+"\t"
//                    +"personID"+"\t"+"phoneNumber"+"\t");
            System.out.println(u.getUsername()+"\t"+u.getPassword()+"\t"
                    +u.getPersonID()+"\t"+u.getPhoneNumber());
        }
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() != 11){
            return false;
        }
        if(phoneNumber.startsWith("0")){
            return false;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if(!(c>='0' && c<='9')){
                return false;
            }
        }
        return true;
    }

    private static boolean checkPersonID(String personID) {
        if(personID.length() != 18){
            return false;
        }
        //不能以0开头
        if(personID.startsWith("0")){
            return false;
        }
        //前17位必须是数字
        for (int i = 0; i < personID.length()-1; i++) {
            char c = personID.charAt(i);
            if(!(c>='0' && c<='9')){
                return false;
            }
        }
        // 最后一位是数字或X
        char endChar = personID.charAt(personID.length()-1);
        return endChar >= '0' && endChar <= '9' || endChar == 'X';
    }

    private static boolean contains(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if(user.getUsername().equals(username)){
                return true;
            };
        }
        return false;
    }

    private static boolean checkUsername(String username) {
        // 用户名只能是3~15位
        int len = username.length();
        if (len < 3 || len > 15) {
            return false;
        }
        // 继续校验：只能是字母加数字的组合
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if(!((c>='a' && c<= 'z')|| (c>='A' && c<='Z')|| (c>='0' && c<='9'))){
                return false;
            }
        }
        // username不能是纯数字组合
        int count = 0;
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if((c>='a' && c<= 'z')|| (c>='A' && c<='Z')){
                count++;
                break;
            }
        }
        return count != 0;
    }



    private static boolean checkUserInfo(ArrayList<User> list, User userInfo) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if(user.getUsername().equals(userInfo.getUsername()) && user.getPassword().equals(userInfo.getPassword())){
                System.out.println("***登录成功,现在开始使用学生管理系统***");
                return true;
            }
        }
        return false;
    }


    private static String getCode(){
        //创建一个集合存放大写和小写字母
        ArrayList<Character> charList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            charList.add((char)('a'+i));
            charList.add((char)('A'+i));
        }
        //随机抽取4个字母
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = (int)(Math.random()*charList.size());
            char c = charList.get(index);
            sb.append(c);
        }
        //随机一个数字并添加到sb中
        int num = (int)(Math.random()*10);
        sb.append(num);
        char [] arr = sb.toString().toCharArray();
        //从arr中随机抽取一个索引
        int arrIndex = (int)(Math.random()*arr.length);
        char temp = arr[arrIndex];
        arr[arrIndex] = arr[arr.length-1];
        arr[arr.length-1] = temp;
        String code = new String(arr);
        System.out.println("验证码："+ code);
        return code;
    }
}
