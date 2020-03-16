package cn.s3bit.th902.gamecontents;

import com.badlogic.gdx.utils.*;
import java.util.*;
import java.util.concurrent.*;

public class ObjectPool {

	private static Pool<Entity> entityPool=new Pool<Entity>(512){
        @Override
        protected Entity newObject() {
			final Entity entity=new Entity();
			entity.mComponents = new TreeMap<>(new Comparator<Class<?>>() {

					@Override
					public int compare(Class<?> o1, Class<?> o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
			Entity.postUpdate.add(new Runnable(){

					@Override
					public void run() {
						Entity.instances.add(entity);
					}
				});
			return entity;
        }
    };

	public static Entity getEntity() {
		return entityPool.obtain();
	}

	public static void recycle(Entity e) {

		entityPool.free(e);
	}
}
