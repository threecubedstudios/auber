package com.threecubed.auber.save;
//first is the position on NPC, second is the type of them in the position
//then is position and health of player
//[System.Serializable]

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Player;
import com.badlogic.gdx.utils.Json;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.threecubed.auber.entities.Player.*;

public class Save() {
    public static Save current;

    public List<Float> entityPositionX = new List<Float>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Float> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Float aFloat) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Float> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Float> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Float get(int index) {
            return null;
        }

        @Override
        public Float set(int index, Float element) {
            return null;
        }

        @Override
        public void add(int index, Float element) {

        }

        @Override
        public Float remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Float> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Float> listIterator(int index) {
            return null;
        }

        @Override
        public List<Float> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    public List<Float> entityPositionY = new List<Float>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Float> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Float aFloat) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Float> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Float> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Float get(int index) {
            return null;
        }

        @Override
        public Float set(int index, Float element) {
            return null;
        }

        @Override
        public void add(int index, Float element) {

        }

        @Override
        public Float remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Float> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Float> listIterator(int index) {
            return null;
        }

        @Override
        public List<Float> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    public List<Integer> entityType = new List<Integer>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Integer> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Integer integer) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Integer> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Integer> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Integer get(int index) {
            return null;
        }

        @Override
        public Integer set(int index, Integer element) {
            return null;
        }

        @Override
        public void add(int index, Integer element) {

        }

        @Override
        public Integer remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Integer> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Integer> listIterator(int index) {
            return null;
        }

        @Override
        public List<Integer> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    public float health;


    public Save CreatSave(World world){
        Save save = new Save();
        for (GameEntity entity : world.getEntities()) {
            save.entityPositionX.add(entity.getCenterX());
            save.entityPositionY.add(entity.getCenterY());
            save.entityType.add(entity.entityType);
        }
        save.health = Player.health;
        return save;
    }

    public void SaveJson(World world){
        Json json = new Json();
        Save save = CreatSave(world);
        String jsonStr = json.toJson(save);
        FileHandle file = Gdx.files.local("save.json");
        //True means append, false means overwrite.
        file.writeString(jsonStr, false);
    }

    public void LoadJson(){
        FileHandle file = Gdx.files.local("save.json");
        String save = file.readString();
        Json json = new Json();
        entityPositionX = json.fromJson(float.class, );


    }

}