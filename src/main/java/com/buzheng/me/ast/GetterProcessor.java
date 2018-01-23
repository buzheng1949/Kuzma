package com.buzheng.me.ast;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Created by buzheng on 18/1/23.
 * 编译期生成getter方法
 * 1、获取AST抽象语法树
 * 2、获取抽象语法树节点
 * 3、获取节点属性并进行处理生成方法
 * 4、方法加入原先的AST
 * 5、生成class文件
 */
@SupportedAnnotationTypes("com.buzheng.me.ast.BuZhengGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetterProcessor extends AbstractProcessor {

    /**
     * A {@code Messager} provides the way for an annotation processor to
     * report error messages, warnings, and other notices.  Elements,
     * annotations, and annotation values can be passed to provide a
     * location hint for the message.  However, such location hints may be
     * unavailable or only approximate.
     */
    private Messager messager;

    /**
     * 抽象语法树
     */
    private JavacTrees javacTrees;

    /**
     * 用来修改抽象语法树的
     */
    private TreeMaker treeMaker;

    /**
     * 提供创建标识符的方法
     */
    private Names names;

    //节点处理
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(BuZhengGetter.class);
        set.forEach(element -> {
            JCTree jcTree = javacTrees.getTree(element);
            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    //搞事情 注意这里的list是它自己重写的
                    List<JCTree.JCVariableDecl> treeList = List.nil();
                    for (JCTree tree : jcClassDecl.defs) {
                        if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) tree;
                            //把需要处理的节点获取出来 就是value
                            treeList = treeList.append(jcVariableDecl);
                            messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName());
                        }
                    }
                    treeList.forEach(jcVariableDecl -> {
                        //对value进行处理 增加方法
                        jcClassDecl.defs = jcClassDecl.defs.prepend(invokeGetter(jcVariableDecl));
                    });
                    super.visitClassDef(jcClassDecl);
                }
            });
        });
        return true;
    }

    /**
     * 处理节点 生成getter
     *
     * @param jcVariableDecl
     * @return
     */
    private JCTree.JCMethodDecl invokeGetter(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        //生成返回值
        statements.append(treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName())));
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        //构建方法
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), methodName(jcVariableDecl.getName()), jcVariableDecl.vartype, List.nil(), List.nil(), List.nil(), body, null);
    }

    /**
     * 生成get方法
     *
     * @param name
     * @return
     */
    private Name methodName(Name name) {
        String sourceName = name.toString();
        Name newName = names.fromString("get" + sourceName.substring(0, 1).toUpperCase() + sourceName.substring(1, sourceName.length()));
        return newName;
    }


    //获取初始化信息 主要是一些环境信息
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }
}
