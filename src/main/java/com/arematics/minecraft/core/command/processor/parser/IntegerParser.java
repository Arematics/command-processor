package com.arematics.minecraft.core.command.processor.parser;

public class IntegerParser extends CommandParameterParser<Integer> {

    @Override
    public Integer doParse(String value) throws ParserException {
        try{
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            throw new ParserException(value + " must be a number");
        }
    }
}
