package ma.ensate.rh.ensate_rh_backend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NiveauConverter implements AttributeConverter<Diplome.Niveau, String> {
    
    @Override
    public String convertToDatabaseColumn(Diplome.Niveau niveau) {
        if (niveau == null) {
            return null;
        }
        switch (niveau) {
            case Bac_Plus_2:
                return "Bac+2";
            default:
                return niveau.name();
        }
    }
    
    @Override
    public Diplome.Niveau convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if ("Bac+2".equals(dbData)) {
            return Diplome.Niveau.Bac_Plus_2;
        }
        try {
            return Diplome.Niveau.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

