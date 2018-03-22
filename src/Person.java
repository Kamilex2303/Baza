public class Person {
    private String name;
    private String surname;
    private String birthday_date;
    private String job_position;
    private float salary;

    public Person(){

    };

    public Person(String name, String surname, String birthday_date, String job_position, float salary) {
        this.name = name;
        this.surname = surname;
        this.birthday_date = birthday_date;
        this.job_position = job_position;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday_date() {
        return birthday_date;
    }

    public void setBirthday_date(String birthday_date) {
        this.birthday_date = birthday_date;
    }

    public String getJob_position() {
        return job_position;
    }

    public void setJob_position(String job_position) {
        this.job_position = job_position;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
