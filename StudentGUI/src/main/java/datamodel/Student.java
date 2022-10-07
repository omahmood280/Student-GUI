package datamodel;

public class Student {
    private String studId;
    private String yearOfStudy;
    private String module1;
    private String module2;
    private String module3;



      // instance variables are set accordingly

    public Student(String id,String yos,String m1,String m2,String m3 ) {
        studId = id;
        yearOfStudy = yos;
        module1 = m1;
        module2 = m2;
        module3 = m3;
    }

// getters

    public String getStudentId() {
        return studId;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public String getModule1() {
        return module1;
    }

    public String getModule2() {
        return module2;
    }

    public String getModule3() {
        return module3;
    }



    // including a toString() that returns the studId

    public String getStringStudID() {

        return studId.toString();
    }


}
