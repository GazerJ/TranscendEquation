import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Strea {
    private  String name;
    private  int age;
    private  double salary;
    private  String sex;
    private  String dept;

    public Strea(String name, int age, double salary, String sex , String dept){
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.sex=sex;
        this.dept=dept;
    }
    public String toString(){
        System.out.println(name+sex+dept);
        return name+sex+dept;
    }
    static List<Strea> getEmplist(){
        List<Strea> list = new ArrayList<Strea>();
        list.add(new Strea("老张",40,9000,"男","运营部门"));
        list.add(list.get(0));
        list.add(new Strea("小张",20,7000,"女","销售部门"));
        return  list;
    }

    public static void main(String[] args) {

        List<Strea> list=getEmplist();
        Stream<Strea> s=list.stream();
        //System.out.println( s.count());

        s=s.distinct();
        System.out.println( s.count());
        s.close();
    }


}
