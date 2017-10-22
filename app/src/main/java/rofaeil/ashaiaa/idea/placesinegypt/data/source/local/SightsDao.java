package rofaeil.ashaiaa.idea.placesinegypt.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rofaeil.ashaiaa.idea.placesinegypt.data.Sight;

/**
 * defines all operation that room can handle for {@link Sight} table in database
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

@Dao
public interface SightsDao {

    /**
     * gets all sights from database
     *
     * @return list of {@link Sight}
     */
    @Query("SELECT * FROM sight")
    List<Sight> getAllSights();

    /**
     * insert sight in sights table in database
     *
     * @param sight sight that will be inserted
     */
    @Insert
    void insertSight(Sight sight);

    /**
     * insert sights in sights table in database
     *
     * @param sightList sights that will be inserted
     */
    @Insert
    void insertSights(List<Sight> sightList);

    /**
     * update sight's data on database
     *
     * @param sight sight that will be updated
     */
    @Update
    void updateSight(Sight sight);

    /**
     * update sight's data on database
     *
     * @param sightList list of sights that will be updated
     */
    @Update
    void updateSights(List<Sight> sightList);

    /**
     * gets all featured sights from database
     *
     * @return list of {@link Sight}
     */
    @Query("SELECT * FROM sight WHERE isFeatured = 1")
    List<Sight> getFeaturedSights();

    /**
     * gets explore sights from database
     *
     * @return list of {@link Sight}
     */
    @Query("SELECT * FROM sight WHERE isFeatured = 0")
    List<Sight> getExploreSights();

    /**
     *  gets sight by id from database
     *
     * @return list of {@link Sight}
     */
    @Query("SELECT * FROM sight WHERE id = :id")
    Sight getSightWithId(int id);
}
