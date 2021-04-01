package ba.unsa.etf.usermicroservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleName {
    ROLE_ADMIN, ROLE_CLIENT
}
/*public enum RoleName {
    ROLE1("ROLE_ADMIN"),
    ROLE2("ROLE_CLIENT");

    private String role;

    private RoleName(String role) {
        this.role = role;
    }

    @JsonCreator
    public static RoleName decode(final String role) {
        return Stream.of(RoleName.values()).filter(targetEnum -> targetEnum.role.equals(role)).findFirst().orElse(null);
    }
}

  /*  @JsonValue
    public String getRole() {
        return role;
    }
}*/