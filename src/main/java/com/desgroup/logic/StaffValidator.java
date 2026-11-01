package com.desgroup.logic;

import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.interfaces.rulesInterfaces.IStaffValidator;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;

public class StaffValidator implements IStaffValidator {

    private final IStaffRepository staffRepository;

    public StaffValidator(IStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public void validate(String name, String email, String phone, String password, Integer currentId) {
        RequiredFields.requireNotBlank(name, "nombre");
        RequiredFields.requireNotBlank(email, "correo");
        RequiredFields.requireNotBlank(phone, "teléfono");
        RequiredFields.requireNotBlank(password, "contraseña");

        if (!email.contains("@"))
            throw new BusinessException("El correo no es válido.");
        if (!phone.matches("\\d+"))
            throw new BusinessException("El telefono debe contener solo números.");

        Staff existing = staffRepository.getByEmail(email);
        if (existing != null && !existing.getId().equals(currentId))
            throw new BusinessException("Ya existe un miembro del personal con el correo " + email + ".");
    }
}
