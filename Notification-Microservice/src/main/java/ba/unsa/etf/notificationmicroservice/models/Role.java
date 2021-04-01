package ba.unsa.etf.notificationmicroservice.models;

import ba.unsa.etf.notificationmicroservice.RoleName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


    @Entity
    @Table
    public class Role {
        @Id
        @GeneratedValue
        private Integer id;
        @Enumerated(EnumType.STRING)
        private RoleName roleName;

        public Role(RoleName roleAdmin) {
            this.roleName=roleAdmin;
        }

        public Role() {

        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public RoleName getRoleName() {
            return roleName;
        }

        public void setRoleName(RoleName roleName) {
            this.roleName = roleName;
        }

        @Override
        public String toString() {
            return "Role{" +
                    "id=" + id +
                    ", roleName=" + roleName +
                    '}';
        }
    }

