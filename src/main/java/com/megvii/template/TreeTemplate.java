package com.megvii.template;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个实现了关于树的多种功能的模板抽象类
 * @author Hang Zhao 2019/06/13
 * @param <T>
 */
public abstract class TreeTemplate<T> {

    /**
     * 需要返回的所有的叶子节点
     */
    private List<T> leafs = new ArrayList<T>();

    /**
     * 某节点下的全量的树节点
     */
    private List<T> allNodes = new ArrayList<T>();

    /**
     * 某个节点下的所有特殊节点
     */
    private List<T> specialNodes = new ArrayList<T>();

    /**
     * 传入某个节点，获取它的所有孩子
     * @param node
     * @return
     */
    protected abstract List<T> getChildList(T node);

    /**
     * 传入一个节点，判断这个节点是否为特殊节点
     * @param node
     * @return
     */
    protected abstract boolean isSpecialNode(T node);

    /**
     * 传入一个节点，判断这个节点是否需要下一次递归
     * @param node
     * @return
     */
    protected abstract boolean needNextRecursion(T node);

    /**
     * 传入某个节点，判断它是否为叶子节点
     * @param node
     * @return
     */
    public boolean isLeaf(T node){
        return this.getChildList(node).size() == 0;
    }

    /**
     * 传入某个节点，返回这个节点下的所有叶子节点
     * @param node
     * @return
     */
    public List<T> getAllLeafs(T node){
        List<T> nodes = this.getChildList(node);
        if (nodes != null && !nodes.isEmpty()){
            for (T tmp : nodes) {
                if (isLeaf(tmp)){
                    leafs.add(tmp);
                }else {
                    getAllLeafs(tmp);
                }
            }
        }
        return leafs;
    }


    /**
     * 传入某个节点，获取这个节点下的全量树的节点
     * @param node
     * @return
     */
    public List<T> getAllNodes(T node){
        List<T> nodes = this.getChildList(node);
        if (nodes != null && !nodes.isEmpty()){
            allNodes.addAll(nodes);
            for (T tmp : nodes) {
                //此处是说：如果是不再需要递归的特殊节点就不执行下面的代码,如果业务不需要,needNextRecursion()的实现返回true即可
                if (this.needNextRecursion(tmp)){
                    getAllNodes(tmp);
                }
            }
        }
        return allNodes;
    }

    /**
     * 传入某个节点，获取这个节点下的所有特殊节点
     * @param node
     * @return
     */
    public List<T> getSpecialNodes(T node){
        List<T> nodes = this.getSpecialNodes(node);
        for(T tmp : nodes){
            if (this.isSpecialNode(tmp)){
                this.specialNodes.add(tmp);
            }
            if (!this.isLeaf(tmp)){
                getSpecialNodes(tmp);
            }
        }
        return this.specialNodes;
    }

    /**
     * 应用于某些业务情景，在获取leafs之前先清空
     */
    public void clearLeafList(){
        this.leafs.clear();
    }

    /**
     * 应用于某些业务情景，在获取allNodes之前先清空
     */
    public void clearAllNode(){
        this.allNodes.clear();
    }

    /**
     * 应用于某些业务情景，在获取allNodes之前先清空
     */
    public void clearSpecialNodes(){
        this.specialNodes.clear();
    }

}
