package br.com.app.template.utils;

import com.j256.ormlite.dao.RawRowMapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by artur on 05/08/15.
 *
 * RawRowMapperImpl for OrmLite
 *
 */
public class RawRowMapperImpl implements RawRowMapper<Map<String, String>> {
    @Override
    public Map<String, String> mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i < resultColumns.length; i++) {
            hashMap.put(columnNames[i], resultColumns[i]);
        }

        return hashMap;
    }
}
