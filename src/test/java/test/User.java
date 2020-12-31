package test;

import lombok.Data;

/**
 * @author pengjunlee
 * @create 2020-10-24 11:31
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private boolean gender;

    public User() {
        super();
    }

    public User(Long id, String name, Integer age, boolean gender) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String toString() {
        return "{id:" + this.getId() + ",name:" + this.getName() + ",age:" + this.getAge() + ",gender:" + this.isGender() + "}";
    }

    @Override
    public boolean equals(final Object that) {
        if (that == null || this == null) {
            return false;
        }
        final User user = (User) that;
        return this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
