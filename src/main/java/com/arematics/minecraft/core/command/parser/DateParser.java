package com.arematics.minecraft.core.command.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser extends CommandParameterParser<Date> {

    private String[] PATTERNS = {"dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy",
    "dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm", "dd.MM.yyyy HH:mm"};

    @Override
    public Date doParse(String value) throws ParserException {
        Date date = null;
        for(String pattern: PATTERNS){
            try{
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                date = format.parse(value);
            }catch (ParseException ignore){}
            if(date != null) break;
        }
        if(date == null) throw new ParserException(value + " is not valid date");
        return date;
    }
}
