package controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.json.FastJson;
import model.Course;
import model.Courseselection;
import model.User;
import service.CourseService;

import java.util.ArrayList;
import java.util.List;

@Path("/course")
public class CourseController extends Controller {

    @Inject
    CourseService service;

    public void selectAllCourse(){
        List<Course> courses = service.getAll();
        renderJson("data",courses);
    }
    //学生选课
    public void chooseCourse(){
        String s = getRawData();
        Courseselection selection = FastJson.getJson().parse(s, Courseselection.class);
        boolean choosen  = service.selectSelection(selection);
        System.out.println(choosen);
        if (!choosen){
            System.out.println("您已选择该课程");
            renderJson("full");
            System.out.println("您已选择该课程");
        }
        if (choosen){
            List<Course> courses = service.selectCourseById(selection.getCid());
            Course course  = courses.get(0);
            int capacity = Integer.parseInt(course.getCapacity());
            int selected = Integer.parseInt(course.getSelected());
            if (capacity > selected){
                selected++;
                course.setSelected(String.valueOf(selected));
                System.out.println(course);
                // service.addSelection(selection);
                int flag = service.updateCourse(course);
                service.addSelection(selection);
                System.out.println(flag);
                if (flag > 0){
                    System.out.println("========================");
                    renderJson("success");
                }
            } else {
                renderJson("error");
            }
        }
    }
    //显示学生已选课程
    public void getSelectedCourse(){
        //System.out.println("进入getSelectedCourse方法");
        String s = getRawData();
        System.out.println(s);
        Courseselection selection = FastJson.getJson().parse(s, Courseselection.class);
        List<Courseselection> selections = service.getCidByUid(selection.getUid());
        List<Course> courses = new ArrayList<>();
        for (Courseselection selection1 : selections) {
            List<Course> courses1 = service.selectCourseById(selection1.getCid());
            courses.add(courses1.get(0));
        }
        renderJson("data",courses);
    }

    //学生退课
    public void unChooseCourse(){
        String s = getRawData();
        Courseselection selection = FastJson.getJson().parse(s, Courseselection.class);
        int i = service.deleteSelection(selection);
        if (i > 0){
            List<Course> courses = service.selectCourseById(selection.getCid());
            Course course  = courses.get(0);
            int selected = Integer.parseInt(course.getSelected());
            selected--;
            course.setSelected(String.valueOf(selected));
            int flag = service.updateCourse(course);
            if (flag > 0 ){
                renderJson("success");
            } else {
                renderJson("error");
            }
        }else {
            renderJson("error");
        }
    }

    //根据课程名模糊查询课程
    public void searchCourseByName(){
        String s = getRawData();
        Course parse = FastJson.getJson().parse(s, Course.class);
        List<Course> courses = service.findCourseByName(parse.getCname());
        renderJson("data",courses);
    }

    //根据授课教师查询课程
    public void selectCourseByTeacher(){
        String s = getRawData();
        Course parse = FastJson.getJson().parse(s, Course.class);
        List<Course> courses = service.findCourseByTeacher(parse.getTeacher());
        renderJson("data",courses);
    }
    //修改课程信息
    public void updateCourse(){
        String s = getRawData();
        Course course = FastJson.getJson().parse(s, Course.class);
        int i = service.update(course);
        System.out.println("i = " + i);
        if (i > 0){
            renderJson("success");
        } else {
            renderJson("error");
        }

    }
    //根据课程id删除课程
    public void deleteCourseByCid(){
        String s = getRawData();
        Course course = FastJson.getJson().parse(s, Course.class);
        int flag = service.deleteCourseByCid(course.getCid());
        if (flag > 0){
            renderJson("success");
        } else {
            renderJson("error");
        }
    }
    //添加课程
    public void addCourse(){
        String s = getRawData();
        Course course = FastJson.getJson().parse(s, Course.class);
        boolean flag = service.addCourse(course);
        if (flag){
            renderJson("success");
        } else {
            renderJson("error");
        }
    }
}
