package com.desgroup.logic;

import com.desgroup.interfaces.repositoriesInterfaces.IStaffRepository;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;

public class StaffValidator {

    private final IStaffRepository staffRepository;

    public StaffValidator(IStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void validate(String name, String email, String phone, String password, Integer currentId) {
        requireNotBlank(name, "nombre");
        requireNotBlank(email, "correo");
        requireNotBlank(phone, "teléfono");
        requireNotBlank(password, "contraseña");

        if (!email.contains("@"))
            throw new BusinessException("El correo no es válido.");
        if (!phone.matches("\\d+"))
            throw new BusinessException("El telefono debe contener solo números.");

        Staff existing = staffRepository.getByEmail(email);
        if (existing != null && !existing.getId().equals(currentId))
            throw new BusinessException("Ya existe un miembro del personal con el correo " + email + ".");
    }

    public void requireNotBlank(String value, String fieldLabel) {
        if (value == null || value.isBlank())
            throw new BusinessException("El campo '" + fieldLabel + "' es obligatorio.");
    }
}
