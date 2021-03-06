package simulator.objects;

import utils.Util;

public class VerticalLine extends Line{
	
	private static final double TOLERANCE = 0.000001;
	
	protected VerticalLine(Location initialPoint, Location finalPoint) {
		super(initialPoint, finalPoint);
	}
	
	protected VerticalLine(Location initialPoint, double direction){
		super(initialPoint, direction);
	}
	
	public Location searchIntersectionPoint(VerticalLine line){		
		return null;
	}
	
	public Location searchIntersectionPoint(NormalLine line){
		double y;
		y = line.getY(this.getConstant());
		
		if(this.belongsToTheInterval(y))
			return new Location(this.getConstant(), y);		
		return null;
	}
	
	public Location searchSlopePoint(Line line){
		if(line instanceof NormalLine)
			return searchIntersectionPoint((NormalLine)line);
		return searchIntersectionPoint((VerticalLine) line);
	}
	
	protected boolean belongsToTheInterval(double y){
		if(this.getFinalPoint() != null){
			if(y <= Math.max(this.getInitialPoint().getY(), this.getFinalPoint().getY()) && y >= Math.min(this.getInitialPoint().getY(), this.getFinalPoint().getY())){
				return true;
			}
		}else{
			if(Util.compareDouble(this.getDirection(), 270, TOLERANCE) && y <= this.getInitialPoint().getY())
				return true;
			
			if(Util.compareDouble(this.getDirection(), 90, TOLERANCE) && y >= this.getInitialPoint().getY())
				return true;				
		}
		
		return false;
	}
	
	public double getSlope() {
		return Double.POSITIVE_INFINITY;
	}
	
	public double getConstant() {
		return super.getInitialPoint().getX();
	}

}
