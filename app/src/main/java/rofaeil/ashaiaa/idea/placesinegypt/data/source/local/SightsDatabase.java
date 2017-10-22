package rofaeil.ashaiaa.idea.placesinegypt.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;

/**
 * this class defines sights database
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

@Database(entities = {Sight.class}, version = 1)
public abstract class SightsDatabase extends RoomDatabase {

    public abstract SightsDao sightsDao();

}
