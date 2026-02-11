package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель пользователя Petstore API (request/response).
 * Соответствует Swagger definition User.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("userStatus")
    private Integer userStatus;

    public User() {
    }

    private User(Builder b) {
        this.id = b.id;
        this.username = b.username;
        this.firstName = b.firstName;
        this.lastName = b.lastName;
        this.email = b.email;
        this.password = b.password;
        this.phone = b.phone;
        this.userStatus = b.userStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public Integer getUserStatus() { return userStatus; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setUserStatus(Integer userStatus) { this.userStatus = userStatus; }

    public static final class Builder {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private Integer userStatus;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder userStatus(Integer userStatus) { this.userStatus = userStatus; return this; }

        public User build() {
            return new User(this);
        }
    }
}
