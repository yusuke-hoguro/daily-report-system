package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reports")
@NamedQueries({

    /*
     * 注：テーブル名のところ（employees）はJPQLではテーブル名ではなく、エンティティクラス名
     * from句にはエンティティ名とそのエンティティのエイリアス(別名)を記述 FROM Employee e
     * select句にエンティティを指定すると、エンティティの全プロパティが取得される（select *に近い）
     */

    //テーブルからIDの降順でデータをすべて取得するJPQL
    @NamedQuery(
            name = "getAllReports",
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
            ),
    //テーブルからデータの数を取得するJPQL
    @NamedQuery(
            name = "getReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r"
            )
})
@Entity
public class Report {

    //GeneratedValue 主キー値を自動採番すること
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //日報を登録した社員の番号 Employeeクラスのidで外部キー設定
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    //いつの日報か Date : 年月日のみ
    @Column(name = "report_date", nullable = false)
    private Date report_date;

    //日報のタイトル
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    //日報の内容 Lob : Lobを指定すれば改行もデータベースに保存される
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    //作成時間 Timestamp : 年月日の他に時分秒（ミリ秒）
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    //更新時間
    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;


    //以下、フィールドのgetter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}
