package com.voytovichs.todomanager.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.voytovichs.todomanager.mainactivity.TaskItem;

import java.sql.SQLException;

/**
 * Created by itegulov on 10/07/15.
 */
public class TaskDAO extends BaseDaoImpl<TaskItem, Integer> {
    protected TaskDAO(ConnectionSource connectionSource, Class<TaskItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    
}
