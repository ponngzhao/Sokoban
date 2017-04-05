package J1030414313;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;

public class SokobanGui extends JFrame implements KeyListener{
    private Map gameMap;
    private Map roundMap1;
    private Map roundMap2;
    private Map roundMap3;
    private Map roundMap4;
    private Map roundMap5;
    private Map roundMap6;
    SokobanGui()throws IOException{
        super("Sokoban");
        this.setLayout(null);
        roundMap1 = new Map("roundMap/map1.txt");
        roundMap2 = new Map("roundMap/map2.txt");
        roundMap3 = new Map("roundMap/map3.txt");
        roundMap4 = new Map("roundMap/map4.txt");
        roundMap5 = new Map("roundMap/map5.txt");
        roundMap6 = new Map("roundMap/map6.txt");
        gameMap = roundMap1;
        this.setSize(1000,500);
        this.setBackground(Color.gray);
        addRestartButton();
        addRoundButton();
        setFocusable(true);
        addMenu();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
    }
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < this.gameMap.getRow(); i++) {
            for (int j = 0; j < this.gameMap.getColumn(); j++) {
                paintBrick(g, 330 + 50 * j, 50 + 50 * i, this.gameMap.dynamicMap[i][j]);
            }
        }
        paintStructure(g);
    }
    /*在x,y画type类型的砖*/
    private void paintBrick(Graphics g,int x,int y,int type){
        switch(type){
            case 0:
                paintCanCoverBrick(g,x,y);
                break;
            case 1:
                paintNotMoveBrick(g,x,y);
                break;
            case 2:
                paintBox(g,x,y);
                break;
            case 3:
                paintTargetBrick(g,x,y);
                break;
            case 4:
                paintSuccessfulBox(g,x,y);
                break;
            case 5:
                paintPeople(g,x,y);
                break;
            case 7:
                paintPeopleAndTargetBrick(g,x,y);
                break;
        }

    }
    /*每次移动位置后，画变化了的砖*/
    private void paintMovedBrick(Graphics g){
        for(int i = 0;i<gameMap.getRow();i++){
            for(int j = 0;j<gameMap.getColumn();j++){
                if(gameMap.dynamicMap[i][j]!=gameMap.tempDynamicMap[i][j]){
//                    System.out.println(gameMap.dynamicMap[i][j]+":"+gameMap.tempDynamicMap[i][j]);
                    paintBrick(g, 330 + 50 * j, 50 + 50 * i, this.gameMap.dynamicMap[i][j]);
                }
            }
        }
    }
    private void paintBox(Graphics g,int x,int y){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        BasicStroke stroke = new BasicStroke(1);
        g2d.setStroke(stroke);
        g2d.setColor(Color.black);
        RoundRectangle2D rectBig = new RoundRectangle2D.Double(x,y,50,50,8,8);
        g2d.draw(rectBig);
        Color fillColor1 = new Color(224,175,136);
        Rectangle2D rectUp = new Rectangle2D.Double(x+6,y,38,8);
        Rectangle2D rectDown = new Rectangle2D.Double(x+6,y+42,38,9);
        Rectangle2D rectLeft = new Rectangle2D.Double(x,y+6,8,38);
        Rectangle2D rectRight = new Rectangle2D.Double(x+44,y+6,6,38);
        g2d.setColor(fillColor1);
        g2d.fill(rectUp);
        g2d.fill(rectDown);
        g2d.fill(rectLeft);
        g2d.fill(rectRight);
        Rectangle2D rectBig2 = new Rectangle2D.Double(x+8,y+7,35,35);
        Color fillColoe2 = new Color(161,66,30);
        g2d.setColor(fillColoe2);
        g2d.fill(rectBig2);
        g2d.setColor(Color.BLACK);
        g2d.draw(rectBig2);
        g2d.setColor(fillColor1);
        Rectangle2D rect1 = new Rectangle2D.Double(x+6,y+7,50,6);
        AffineTransform trans1 = new AffineTransform();
        trans1.rotate(45*3.1415927/180.0,x+10,y+10);
        g2d.setTransform(trans1);
        g2d.fill(rect1);
        Rectangle2D rect2 = new Rectangle2D.Double(x+6,y+40,50,6);
        AffineTransform trans2 = new AffineTransform();
        trans2.rotate(-45*3.1415927/180.0,x+8,y+40);
        g2d.setTransform(trans2);
        g2d.fill(rect2);
        AffineTransform trans3 = new AffineTransform();
        trans2.rotate(0,0,0);
        g2d.setTransform(trans3);
        Color fillColor3 = new Color(107,57,6);
        Rectangle2D rectSmall1 = new Rectangle2D.Double(x,y,6,6);
        Rectangle2D rectSmall2 = new Rectangle2D.Double(x+44,y,6,6);
        Rectangle2D rectSmall3 = new Rectangle2D.Double(x,y+44,6,6);
        Rectangle2D rectSmall4 = new Rectangle2D.Double(x+44,y+44,6,6);
        g2d.setColor(fillColor3);
        g2d.fill(rectSmall1);
        g2d.fill(rectSmall2);
        g2d.fill(rectSmall3);
        g2d.fill(rectSmall4);
    }
    private void paintCanCoverBrick(Graphics g,int x,int y){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        g2d.setColor(Color.black);
        BasicStroke stroke = new BasicStroke(2);
        g2d.setStroke(stroke);
        Rectangle2D rectBig = new Rectangle2D.Double(x,y,50,50);
        Color fillColor1 = new Color(107,57,6);
        g2d.setColor(fillColor1);
        g2d.fill(rectBig);
        g2d.setColor(Color.black);
        Rectangle2D rectBig2 = new Rectangle2D.Double(x,y+1,25,48);
        g2d.draw(rectBig2);
        Line2D line = new Line2D.Double(x+25,y+25,x+49,y+25);
        g2d.draw(line);
    }
    private void paintPeople(Graphics g,int x,int y){
        paintCanCoverBrick(g,x,y);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        BasicStroke stroke = new BasicStroke(3);
        g2d.setStroke(stroke);
        Color color1 = new Color(255,147,6);
        Arc2D arcOut = new Arc2D.Double(x+5,y+5,40,40,0,360,Arc2D.OPEN);
        g2d.setColor(Color.WHITE);
        g2d.draw(arcOut);
        Arc2D arcIn = new Arc2D.Double(x+16,y+16,20,20,0,360,Arc2D.OPEN);
        g2d.setColor(color1);
        g2d.fill(arcIn);
    }
    private void paintSuccessfulBox(Graphics g,int x,int y){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        BasicStroke stroke = new BasicStroke(1);
        g2d.setStroke(stroke);
        g2d.setColor(Color.black);
        RoundRectangle2D rectBig = new RoundRectangle2D.Double(x,y,50,50,8,8);
        g2d.draw(rectBig);
        Color fillColor1 = new Color(224,175,136);
        Rectangle2D rectUp = new Rectangle2D.Double(x+6,y+2,38,6);
        Rectangle2D rectDown = new Rectangle2D.Double(x+6,y+42,38,7);
        Rectangle2D rectLeft = new Rectangle2D.Double(x+2,y+6,6,38);
        Rectangle2D rectRight = new Rectangle2D.Double(x+44,y+6,6,38);
        g2d.setColor(fillColor1);
        g2d.fill(rectUp);
        g2d.fill(rectDown);
        g2d.fill(rectLeft);
        g2d.fill(rectRight);
        Rectangle2D rectBig2 = new Rectangle2D.Double(x+8,y+7,35,35);
        Color fillColoe2 = new Color(17, 170, 51);
        g2d.setColor(fillColoe2);
        g2d.fill(rectBig2);
        g2d.setColor(Color.BLACK);
        g2d.draw(rectBig2);
        g2d.setColor(fillColor1);
        Rectangle2D rect1 = new Rectangle2D.Double(x+6,y+7,50,6);
        AffineTransform trans1 = new AffineTransform();
        trans1.rotate(45*3.1415927/180.0,x+10,y+10);
        g2d.setTransform(trans1);
        g2d.fill(rect1);
        Rectangle2D rect2 = new Rectangle2D.Double(x+6,y+40,50,6);
        AffineTransform trans2 = new AffineTransform();
        trans2.rotate(-45*3.1415927/180.0,x+8,y+40);
        g2d.setTransform(trans2);
        g2d.fill(rect2);
    }
    private void paintNotMoveBrick(Graphics g,int x, int y){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        BasicStroke stroke = new BasicStroke(2);
        g2d.setStroke(stroke);
        Rectangle2D rectBig = new Rectangle2D.Double(x,y,50,50);
        Color fillColor1 = new Color(180,180,180);
        g2d.setColor(fillColor1);
        g2d.fill(rectBig);
        g2d.setColor(Color.WHITE);
        Rectangle2D rectWhite1 = new Rectangle2D.Double(x+1,y+1,48,2);
        Rectangle2D rectWhite2 = new Rectangle2D.Double(x+1,y+1,2,48);
        Rectangle2D rectWhite3 = new Rectangle2D.Double(x+1,y+25,48,4);
        Rectangle2D rectWhite4 = new Rectangle2D.Double(x+25,y+25,4,24);
        g2d.fill(rectWhite1);
        g2d.fill(rectWhite2);
        g2d.fill(rectWhite3);
        g2d.fill(rectWhite4);
        g2d.setColor(Color.black);
        Rectangle2D rect1 = new Rectangle2D.Double(x+2,y+25,46,24);
        g2d.draw(rect1);
        Line2D line1 = new Line2D.Double(x+25,y+25,x+25,y+49);
        Line2D line2 = new Line2D.Double(x+48,y+1,x+48,y+25);
        g2d.draw(line1);
        g2d.draw(line2);
    }
    private void paintTargetBrick(Graphics g,int x,int y){
        paintCanCoverBrick(g,x,y);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        BasicStroke stroke = new BasicStroke(2);
        g2d.setStroke(stroke);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        Arc2D arc1 = new Arc2D.Double(x+12,y+12,10,10,0,360,Arc2D.OPEN);
        Arc2D arc2 = new Arc2D.Double(x+12,y+12,25,25,0,360,Arc2D.OPEN);
        Color fillColor1 = new Color(17, 170, 51);
        g2d.setColor(fillColor1);
        g2d.fill(arc2);
//        g2d.setColor(Color.WHITE);
//        g2d.fill(arc1);
    }
    private void paintPeopleAndTargetBrick(Graphics g,int x,int y){
        paintCanCoverBrick(g,x,y);
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans0 = new AffineTransform();
        trans0.rotate(0,0,0);
        g2d.setTransform(trans0);
        BasicStroke stroke = new BasicStroke(3);
        g2d.setStroke(stroke);
        Color color1 = new Color(17, 170, 51);
        Arc2D arcOut = new Arc2D.Double(x+5,y+5,40,40,0,360,Arc2D.OPEN);
        g2d.setColor(Color.WHITE);
        g2d.draw(arcOut);
        Arc2D arcIn = new Arc2D.Double(x+16,y+16,20,20,0,360,Arc2D.OPEN);
        g2d.setColor(color1);
        g2d.fill(arcIn);
    }
    private void paintStructure(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(300,55,300,500);
        g.drawLine(0,100,300,100);
        Font font = new Font("楷体",Font.BOLD,30);
        g.setFont(font);
        g.drawString("推箱子",100,85);
    }
    private void addRoundButton(){
        JButton roundButton1 = new JButton("第一关");
        JButton roundButton2 = new JButton("第二关");
        JButton roundButton3 = new JButton("第三关");
        JButton roundButton4 = new JButton("第四关");
        JButton roundButton5 = new JButton("第五关");
        JButton roundButton6 = new JButton("第六关");
        roundButton1.setFocusable(false);
        roundButton2.setFocusable(false);
        roundButton3.setFocusable(false);
        roundButton4.setFocusable(false);
        roundButton5.setFocusable(false);
        roundButton6.setFocusable(false);
        roundButton1.setVisible(true);
        roundButton2.setVisible(true);
        roundButton3.setVisible(true);
        roundButton4.setVisible(true);
        roundButton5.setVisible(true);
        roundButton6.setVisible(true);
        roundButton1.setBounds(20,100,75,50);
        roundButton2.setBounds(110,100,75,50);
        roundButton3.setBounds(200,100,75,50);
        roundButton4.setBounds(20,170,75,50);
        roundButton5.setBounds(110,170,75,50);
        roundButton6.setBounds(200,170,75,50);
        roundButton1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameMap = roundMap1;
//                        initGui();
//                        setFocus();
//                        getContentPane().addKeyListener(getContentPane());
//                        getContentPane().setFocusable(true);
                        repaint();
                    }
                }
        );
        roundButton2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        gameMap = new Map("map2.txt");
//                        mapAddress = "map2.txt";
//                        SokobanGui test = new SokobanGui(mapAddress);
//                        initMap();
                        setFocusable(true);
                        gameMap = roundMap2;
//                        initGui();
                        repaint();
                    }
                }
        );
        roundButton3.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameMap = roundMap3;
//                        initGui();
                        setFocusable(true);
                        repaint();
                    }
                }
        );
        roundButton4.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameMap = roundMap4;
//                        initGui();
                        setFocusable(true);
                        repaint();
                    }
                }
        );
        roundButton5.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameMap = roundMap5;
//                        initGui();
                        setFocusable(true);
                        repaint();
                    }
                }
        );
        roundButton6.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameMap = roundMap6;
//                        initGui();
                        setFocusable(true);
                        repaint();
                    }
                }
        );
        this.add(roundButton1);
        this.add(roundButton2);
        this.add(roundButton3);
        this.add(roundButton4);
        this.add(roundButton5);
        this.add(roundButton6);
    }
    private void addRestartButton() {
        JButton restartButton = new JButton("重新开始本关");
        restartButton.setFocusable(false);
        restartButton.setVisible(true);
        restartButton.setBounds(85,250,120,60);
        restartButton.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e)  {
                        gameMap.reSetMap();
                        repaint();
                    }
                }
        );
        this.add(restartButton);
    }
    private void addMenu(){
        JMenuBar functionBar = new JMenuBar();
        JMenu aboutMenu = new JMenu("关于");
        JMenuItem authorItem = new JMenuItem("作者");
        authorItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,"          姓名：赵宏鹏\n          班级：计科1403\n          学号：1030414313","Author",JOptionPane.PLAIN_MESSAGE);
                    }
                }
        );
        aboutMenu.add(authorItem);
        functionBar.add(aboutMenu);
        setJMenuBar(functionBar);
    }
    public void keyPressed(KeyEvent e){    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){
            gameMap.movePeople(1);
            paintMovedBrick(getGraphics());
//            repaint();
            if(gameMap.isSucceed()){
                JOptionPane.showConfirmDialog(null,"恭喜过关","恭喜过关",JOptionPane.DEFAULT_OPTION);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            gameMap.movePeople(2);
            paintMovedBrick(getGraphics());
//            repaint();
            if(gameMap.isSucceed()){
                JOptionPane.showConfirmDialog(null,"恭喜过关","恭喜过关",JOptionPane.DEFAULT_OPTION);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            gameMap.movePeople(3);
            paintMovedBrick(getGraphics());
//            repaint();
            if(gameMap.isSucceed()){
                JOptionPane.showConfirmDialog(null,"恭喜过关","恭喜过关",JOptionPane.DEFAULT_OPTION);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            gameMap.movePeople(4);
            paintMovedBrick(getGraphics());
//            repaint();
            if(gameMap.isSucceed()){
                JOptionPane.showConfirmDialog(null,"恭喜过关","恭喜过关",JOptionPane.DEFAULT_OPTION);
            }
        }
    }
    public void keyTyped(KeyEvent e){    }
}