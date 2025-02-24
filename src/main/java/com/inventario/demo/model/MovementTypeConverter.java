package com.inventario.demo.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MovementTypeConverter implements AttributeConverter<Movimiento.MovementType, String> {

    @Override
    public String convertToDatabaseColumn(Movimiento.MovementType type) {
        if (type == null) {
            return null;
        }
       
        return "CAST('" + type.name() + "' AS movementtype)"; 
    }

    @Override
    public Movimiento.MovementType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Movimiento.MovementType.valueOf(dbData);
    }
}