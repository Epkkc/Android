package com.elegion.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.elegion.data.model.project.Cover;
import com.elegion.data.model.project.Owner;
import com.elegion.data.model.project.Project;
import com.elegion.data.model.user.Image;
import com.elegion.data.model.user.User;

/**
 * Created by Vladislav Falzan.
 */

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 3)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
