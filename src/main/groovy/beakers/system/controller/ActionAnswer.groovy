package beakers.system.controller

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["controller", "action", "code"])
class ActionAnswer {

    Class controller
    String action

    String alert = "none"
    def code = ""

    boolean reload = false

    String message = ""
    List fields = []

    def data

    def leftShift(FieldError fieldError) {
        this.fields.removeAll { it.name == fieldError.name }
        this.fields << fieldError
        return this
    }

    def plus(String action) {
        if (action == AbstractMvcController.RELOAD) {
            this.reload = true
        }
        return this
    }

    def minus(String action) {
        if (action == AbstractMvcController.RELOAD) {
            this.reload = false
        }
        return this
    }


    @Override
    public String toString() {
        return "ActionAnswer{" +
                "alert='" + alert + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", fields=" + fields +
                ", data=" + data +
                '}';
    }
}
