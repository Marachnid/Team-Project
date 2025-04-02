package team.project.entity;

public class Profile {

    private int id;
    private int age;
    private String sex;
    private int height;
    private int weight;


    public Profile() {}


    public Profile(int id, int age, String sex, int height, int weight) {
        this.id = id;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
