package Entities;

public class ReturnIntEntity {
	LivingEntity entity;
	int result;
	
	public ReturnIntEntity(int result, LivingEntity entity) {
		this.entity = entity;
		this.result = result;
	}
	
	public LivingEntity getEntity() {
		return entity;
	}
	
	public int getInt() {
		return result;
	}
	
	public void setInt(int result) {
		this.result = result;
	}
	
	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}
}
