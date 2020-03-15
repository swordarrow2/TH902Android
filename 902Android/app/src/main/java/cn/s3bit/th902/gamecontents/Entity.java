package cn.s3bit.th902.gamecontents;

import cn.s3bit.th902.*;
import cn.s3bit.th902.gamecontents.components.*;
import java.util.*;
import java.util.Map.*;
import java.util.concurrent.*;

public class Entity {
	public static TreeSet<Entity> instances = new TreeSet<Entity>(new Comparator<Entity>() {

			@Override
			public int compare(Entity o1, Entity o2) {
				if (o1 == o2)
					return 0;
				if (o1.updateOrder != o2.updateOrder)
					return Integer.compare(o1.updateOrder, o2.updateOrder);
				return Long.compare(o1.identity, o2.identity);
			}
		});
	public static LinkedBlockingQueue<Runnable> postUpdate = new LinkedBlockingQueue<>();

	private TreeMap<Class<?>, Component> mComponents;
	public int updateOrder;
	public final long identity;
	public static long identitySender = 0;
	private LinkedBlockingQueue<Class<?>> toDel;
	private Entity() {
		identity = ++identitySender;
	}

	public static Entity Create() {
		return Create(0);
	}

	public static Entity Create(int updateOrder) {
		final Entity entity = new Entity();
		entity.mComponents = new TreeMap<>(new Comparator<Class<?>>() {

				@Override
				public int compare(Class<?> o1, Class<?> o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
		entity.toDel = new LinkedBlockingQueue<>();
		entity.updateOrder = updateOrder;
		postUpdate.add(new Runnable(){

				@Override
				public void run() {
					instances.add(entity);
				}
			});
		return entity;
	}

	public void Update() {
		Object[] entries =  mComponents.entrySet().toArray();
		for (Object obj : entries) {
			@SuppressWarnings("unchecked")
				Entry<Class<?>, Component> entry = (Entry<Class<?>, Component>) obj;
			entry.getValue().Update();
			if (entry.getValue().isDead()) {
				toDel.add(entry.getKey());
			}
		}
		while (!toDel.isEmpty()) {
			mComponents.remove(toDel.poll());
		}
		if (mComponents.isEmpty()) {
			this.Destroy();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T GetComponent(Class<T> type) {
		T c = (T) mComponents.get(type);
		if (c != null)
			return c;
		for (Class<?> typec : mComponents.keySet()) {
			if (type.isAssignableFrom(typec)) {
				return (T) mComponents.get(typec);
			}
		}
		return null;
	}

	public void AddComponent(Component component) {
		if (component == null)
			throw new NullPointerException();
		Class<?> type = component.getClass();
		if (mComponents.containsKey(type))
			throw new IllegalArgumentException();
		mComponents.put(type, component);
		component.Initialize(this);
	}

	public void Destroy() {
		Component[] components = mComponents.values().toArray(new Component[mComponents.values().size()]);
		for (Component component : components)
			if (!component.isDead()) {
				component.Kill();
			}
		mComponents.clear();
		instances.remove(this);
	}

	public static void Reset() {
		Entity[] entities = instances.toArray(new Entity[instances.size()]);
		for (int i = 0; i < entities.length; i++) {
			entities[i].Destroy();
		}
	}

	public static void UpdateAll() {
		FightScreen.instance.stage.act(1f / 60f);
		Entity[] entities = instances.toArray(new Entity[instances.size()]);
		for (int i = 0; i < entities.length; i++) {
			entities[i].Update();
		}
		while (!postUpdate.isEmpty()) {
			postUpdate.poll().run();
		}
	}
}
