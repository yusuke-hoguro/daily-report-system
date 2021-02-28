package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
* daily_report_systemデータベースのEmployeesデーブルのDTOクラス
* @author yusuke hoguro
* @version 1.0.0
*/

@Entity
@NamedQueries({
    /*
     * 注：テーブル名のところ（employees）はJPQLではテーブル名ではなく、エンティティクラス名
     * from句にはエンティティ名とそのエンティティのエイリアス(別名)を記述 FROM Employee e
     * select句にエンティティを指定すると、エンティティの全プロパティが取得される（select *に近い）
     */

    //テーブルからIDの降順でデータをすべて取得するJPQL
    @NamedQuery(
            name = "getAllEmployees",
            query = "SELECT e FROM Employee AS e ORDER BY e.id DESC"
            ),
    //テーブルからデータの数を取得するJPQL
    @NamedQuery(
            name = "getAllEmployeesCount",
            query = "SELECT COUNT(e) FROM Employee AS e"
            ),
    //指定されて社員番号がデータベースに存在しているかを調べる
    @NamedQuery(
            name = "checkRegisteredCode",
            query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"
            ),
    //社員番号とパスワードが正しいかを調べるJPQL
    @NamedQuery(
            name = "checkLoginCodeAndPassword",
            query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.password = :pass"
            )
})

@Table(name = "employees")

public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * unique :一意制約（すでに存在しているデータは登録できない）
     * nullable :nullを許容するかどうか
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    /*
     * length :入力できる文字数の指定
     *
     * パスワードはハッシュ化した文字列をデータベースへ保存
     * SHA256 というハッシュ関数はどんな文字数の文字列でも必ず、64文字のハッシュ化された文字列にしてくれる。
     * なので64文字までという設定を追加
     */
    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;


    //これより下はフィールドのgetter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
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

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }


}
