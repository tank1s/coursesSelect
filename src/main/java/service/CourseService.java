package service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import model.Course;
import model.Courseselection;

import java.util.List;

public class CourseService {
    public static final Course dao = new Course().dao();
    public static final Courseselection selectionDao = new Courseselection().dao();
    public List<Course> getAll() {
        return dao.findAll();
    }

    public boolean addSelection(Courseselection selection) {
        return new Courseselection().set("id",null)
                .set("cid",selection.getCid())
                .set("uid",selection.getUid())
                .save();
    }

    public List<Course> selectCourseById(String cid) {
        return dao.find("select * from course where cid = '" + cid + "'");
    }

    public int updateCourse(Course course) {
        return Db.update("update course set selected = '" + course.getSelected() + "'" +
                "where cid = '" + course.getCid() + "'");
    }

    public boolean selectSelection(Courseselection selection) {
        List<Record> records = Db.find("select * from courseselection where cid = '" + selection.getCid() + "'" +
                " and uid = '" + selection.getUid() + "'");
        System.out.println("打印records");
        System.out.println(records);
        return records.isEmpty();

    }

    public List<Courseselection> getCidByUid(String uid) {
        return selectionDao.find("select * from courseselection where uid = '" + uid + "'");
    }

    public int deleteSelection(Courseselection selection) {
        return Db.delete("delete from courseselection where uid = '" + selection.getUid() + "'" +
                " and cid = '" + selection.getCid() + "'");
    }

    public List<Course> findCourseByName(String cname) {

        return dao.find("select * from course where cname like '%" + cname + "%'");
    }

    public List<Course> findCourseByTeacher(String teacher) {
        return dao.find("select * from course where teacher = '" + teacher + "'");
    }

    public int  update(Course course) {
        return Db.update("update course set capacity = '" + course.getCapacity() + "'" +
                ",timelength = '" + course.getTimelength() +
                "',place = '" + course.getPlace() + "'" +
                " where cid = '" + course.getCid() + "'");
    }

    public int  deleteCourseByCid(String cid) {
        return Db.delete("delete from course where cid = '" + cid +"'");
    }

    public boolean addCourse(Course course) {
        return new Course().set("cid",course.getCid())
                .set("cname",course.getCname())
                .set("teacher",course.getTeacher())
                .set("capacity",course.getCapacity())
                .set("selected","0")
                .set("timelength",course.getTimelength())
                .set("place",course.getPlace())
                .save();
    }
}
