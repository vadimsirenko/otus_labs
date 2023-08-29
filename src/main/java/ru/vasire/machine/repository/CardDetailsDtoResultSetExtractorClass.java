package ru.vasire.machine.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.vasire.machine.model.dto.CardDetailsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDetailsDtoResultSetExtractorClass implements ResultSetExtractor<List<CardDetailsDto>> {

    @Override
    public List<CardDetailsDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var cardDetailsDtoList = new ArrayList<CardDetailsDto>();
        while (rs.next()) {
            CardDetailsDto cardDetailsDto = new CardDetailsDto(
                    rs.getString("accuntNumber"),
                    rs.getString("cardNumber"),
                    rs.getString("pinCode"),
                    rs.getBigDecimal("balance"));
            cardDetailsDtoList.add(cardDetailsDto);
        }
        return cardDetailsDtoList;
    }
}
