package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Attendance;
import jakarta.ejb.Stateless;

@Stateless
public class AttendanceFacade extends AbstractFacade<Attendance> {

    public AttendanceFacade() {
        super(Attendance.class);
    }
}
