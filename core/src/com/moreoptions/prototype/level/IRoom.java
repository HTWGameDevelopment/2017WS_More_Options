package com.moreoptions.prototype.level;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

/**
 * Created by denwe on 15.11.2017.
 */
public interface IRoom {


    ArrayList<Entity> getEntitiesWithOffset(Offset offset);


    Entity[] getAllEntities();
}
