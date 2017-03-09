package application;


import entities.Airport;

import java.util.ArrayList;

public class Node {
    private Airport org;
    private Airport des;
    private Airport via;
    private Node lastNode;
    private ArrayList<Airport> route;
    private int g;
    private int h;
    private int f;

    public Node(Airport org, Airport des, Airport via) {
        this.org = org;
        this.des = des;
        this.via = via;
        this.g = 0;
        this.h = Calculator.distance(via, des);
        this.f = g + h;
        this.route = new ArrayList<>();
        this.route.add(via);
    }

    public Node(Airport org, Airport des, Airport via, Node lastNode) {
        this.org = org;
        this.des = des;
        this.via = via;
        this.lastNode = lastNode;
        this.g = lastNode.getG() + Calculator.distance(lastNode.getVia(), via);
        this.h = Calculator.distance(via, des);
        this.f = g + h;
        this.route = new ArrayList<>();
        this.route.addAll(lastNode.route);
        this.route.add(via);
    }

    public Node() {
    }

    public Airport getOrg() {
        return org;
    }

    public void setOrg(Airport org) {
        this.org = org;
    }

    public Airport getDes() {
        return des;
    }

    public void setDes(Airport des) {
        this.des = des;
    }

    public Airport getVia() {
        return via;
    }

    public void setVia(Airport via) {
        this.via = via;
    }

    public Node getLastNode() {
        return lastNode;
    }

    public void setLastNode(Node lastNode) {
        this.lastNode = lastNode;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ArrayList<Airport> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Airport> route) {
        this.route = route;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
