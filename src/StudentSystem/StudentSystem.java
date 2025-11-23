package StudentSystem;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        while (true) {
            System.out.println("------------学生管理系统--------------");
            System.out.println("1.添加学生");
            System.out.println("2.删除学生");
            System.out.println("3.修改学生");
            System.out.println("4.查询学生");
            System.out.println("5.退出");
            System.out.println("请输入你的选择：");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent(students);
                case "2" -> deleteStudent(students);
                case "3" -> updateStudent(students);
                case "4" -> queryStudent(students);
                case "5" -> {
                    System.out.println("退出");
                    System.exit(0); // 退出程序(关闭虚拟机)
                }
                default -> System.out.println("没有此选项");
            }
        }
    }
    public static void addStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        String id = null;
        while (true) {
            System.out.println("添加学生id:");
            id = sc.next();
            if(contains(students, id)){
                System.out.println("id已存在");
            }else{
                break;
            }
        }
        System.out.println("添加学生姓名:");
        String name = sc.next();
        System.out.println("添加学生年龄:");
        int age = sc.nextInt();
        System.out.println("添加学生地址:");
        String address = sc.next();
        Student stu = new Student(id, name, age, address);
        students.add(stu);
        System.out.println("添加成功");
    }
    public static void deleteStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除学生的id:");
        String id = sc.next();
        int index = getIndex(students, id);
        if(index>=0){
            students.remove(index);
            System.out.println("删除成功");
        }else{
            System.out.println("没有此id");
        }
    }
    public static void updateStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改学生的id:");
        String id = sc.next();
        int index = getIndex(students,id);
        if(index<=0){
            System.out.println("没有此id");
        }else{
            System.out.println("修改学生的姓名：");
            String newName = sc.next();
            students.get(index).setName(newName);
            System.out.println("修改学生的年龄：");
            int newAge = sc.nextInt();
            students.get(index).setAge(newAge);
            System.out.println("修改学生的地址：");
            String newAddress = sc.next();
            students.get(index).setAddress(newAddress);
        }
    }
    public static void queryStudent(ArrayList<Student> students){
        if(students.isEmpty()){
            System.out.println("没有学生，请添加学生");
        }else {
            System.out.println("*******所有学生列表***********");
            System.out.println("id\t姓名\t年龄\t地址");
            for (int i = 0; i < students.size(); i++) {
                Student stu = students.get(i);
                System.out.println(stu.getId() + "\t" + stu.getName() + "\t" + stu.getAge() + "\t\t" + stu.getAddress());
            }
        }
    }
    public static boolean contains(ArrayList<Student> students, String id) {
        if(getIndex(students, id)>=0){
            return  true;
        }
        return false;
    }
    public static int getIndex(ArrayList<Student> students, String id) {
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            String sid = stu.getId();
            if(sid.equals(id)){
                return i;
            }
        }
        return -1;
    }
}
