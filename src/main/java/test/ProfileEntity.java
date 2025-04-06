package test;

import javax.persistence.*;


/**
 * Main entity class for profiles - maps instance variables to DB table
 */
@Entity
@Table(name = "profiles")
public class ProfileEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "age")
    private int age;

    @Column(name = "height")
    private double height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "sex")
    private String sex;

    @Column(name = "activity")
    private double activity;


    //empty constructor
    public ProfileEntity() {}


    //full constructor including all variables
    public ProfileEntity(int id, int age, double height, double weight, String sex, double activity) {
        this.id = id;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.activity = activity;
    }

    //partial constructor excluding ID for independent calculations without DB interaction
    public ProfileEntity(int age, double height, double weight, String sex, double activity) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.activity = activity;
    }


    /**
     * GET method for ID
     * @return id
     */
    public int getId() {return id;}

    /**
     * SET method for ID
     * @param id id
     */
    public void setId(int id) {this.id = id;}

    /**
     * GET method for age
     * @return age
     */
    public int getAge() {return age;}

    /**
     * SET method for age
     * @param age age
     */
    public void setAge(int age) {this.age = age;}

    /**
     * GET method for height
     * @return height
     */
    public double getHeight() {return height;}

    /**
     * SET method for height
     * @param height height
     */
    public void setHeight(int height) {this.height = height;}

    /**
     * GET method for weight
     * @return weight
     */
    public double getWeight() {return weight;}

    /**
     * SET method for weight
     * @param weight weight
     */
    public void setWeight(int weight) {this.weight = weight;}

    /**
     * GET method for sex-type
     * @return sex-type
     */
    public String getSex() {return sex;}

    /**
     * SET method for sex-type
     * @param sex sex-type
     */
    public void setSex(String sex) {this.sex = sex;}

    /**
     * GET method for activity levels
     * @return activity levels
     */
    public double getActivity() {return activity;}

    /**
     * SET method for activity levels
     * @param activity activity levels
     */
    public void setActivity(int activity) {this.activity = activity;}
}
