// License: GPL. For details, see LICENSE file.
package seachart;

import java.awt.Color;
import java.awt.geom.Point2D;

import render.ChartContext;
import s57.S57map;
import s57.S57map.Feature;
import s57.S57map.GeomIterator;
import s57.S57map.Pflag;
import s57.S57map.Snode;
import s57.S57obj.Obj;
import symbols.Symbols;

/**
 * contains code from Malcolm Herring
 */
public class ChartImage implements ChartContext {
	
	boolean debug = false;

    @Override
    public Point2D.Double getPoint(Snode coord) {
    	
    	if (debug) System.out.println(Math.toDegrees(coord.lat) + " " + Math.toDegrees(coord.lon));
    	Point2D.Double point = new Point2D.Double((((Math.toDegrees(coord.lat)-50) / 8)*10000)-4000, (((Math.toDegrees(coord.lon)-8) / 8)*10000)-0);
    	if (debug) System.out.println(point);
    	return point;//MainApplication.getMap().mapView.getPoint2D(new LatLon(Math.toDegrees(coord.lat), Math.toDegrees(coord.lon)));

    	
    }

    @Override
    public double mile(Feature feature) {
        return 185.2;
    }

    @Override
    public boolean clip() {
        return false;
    }

    @Override
    public Color background(S57map map) {
        if (map.features.containsKey(Obj.COALNE)) {
            for (Feature feature : map.features.get(Obj.COALNE)) {
                if (feature.geom.prim == Pflag.POINT) {
                    break;
                }
                GeomIterator git = map.new GeomIterator(feature.geom);
                git.nextComp();
                while (git.hasEdge()) {
                    git.nextEdge();
                    while (git.hasNode()) {
                        Snode node = git.next();
                        if (node == null)
                            continue;
                        if ((node.lat >= map.bounds.minlat) && (node.lat <= map.bounds.maxlat)
                                && (node.lon >= map.bounds.minlon) && (node.lon <= map.bounds.maxlon)) {
                            return Symbols.Bwater;
                        }
                    }
                }
            }
            return Symbols.Yland;
        } else {
            if (map.features.containsKey(Obj.ROADWY) || map.features.containsKey(Obj.RAILWY)
                    || map.features.containsKey(Obj.LAKARE) || map.features.containsKey(Obj.RIVERS) || map.features.containsKey(Obj.CANALS)) {
                return Symbols.Yland;
            } else {
                return Symbols.Bwater;
            }
        }
    }

    @Override
    public RuleSet ruleset() {
        return RuleSet.ALL;
    }
}
