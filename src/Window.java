import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Window{
    static int [][] matrix = new int [3][3];
    static int row = 0;
    static int col = 0;
    static final int FRAME_DIM = 500;
    static final int FONT_SIZE = 40;
    static JFrame frame = new JFrame("Matrix Det Calculator");
    static GridLayout layout = new GridLayout(3,3);
    static JTextField [][] fieldMatrix =  new JTextField[3][3];
    static boolean [][] negativesMatrix =  new boolean[3][3];
    static JLabel label = new JLabel("MESSAGE");
    Window(){}
    protected static void displayArr(int[][]arr){
        for(int row = 0; row<3; row++){
            for (int col = 0; col < 3; col++){
                System.out.print(arr[row][col] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    protected static void addFields(){
        for(int row = 0; row<3; row++){
            for(int col = 0; col<3; col++){
                JTextField cellField = new JTextField();
                cellField.setEditable(false);
                cellField.setFocusable(false);
                cellField.setHorizontalAlignment(JLabel.CENTER);
                cellField.setFont(new Font("Caladea", Font.BOLD,FONT_SIZE));
                cellField.setCaretColor(Color.white);
                cellField.setBackground(Color.black);
                cellField.setForeground(Color.white);
                frame.add(cellField);
                fieldMatrix[row][col] = cellField;
            }
        }
        frame.revalidate();
    }

    protected static void configureComponents(){
        label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.setSize(FRAME_DIM,FRAME_DIM);
        frame.setLocation(30,20);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLayout(layout);
        frame.setVisible(true);
    }
    protected static void addWindowListeners(){
        KeyListener moveListener = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent typed){
                typed.consume();
            }
            @Override
            public void keyPressed(KeyEvent move){
                int keyCode = move.getKeyCode();
                switch (keyCode){
                    //left or a
                    case 65:
                    case 37:
                        if(col>0){
                            fieldMatrix[row][col].setBackground(Color.BLACK);
                            col--;
                            fieldMatrix[row][col].setBackground(Color.darkGray);
                        }
                        break;
                    //up or w
                    case 87:
                    case 38:
                        if(row>0){
                            fieldMatrix[row][col].setBackground(Color.BLACK);
                            row--;
                            fieldMatrix[row][col].setBackground(Color.darkGray);
                        }
                        break;
                    //right or d
                    case 68:
                    case 39:
                        if(col<2){
                            fieldMatrix[row][col].setBackground(Color.BLACK);
                            col++;
                            fieldMatrix[row][col].setBackground(Color.darkGray);
                        }
                        break;
                    //down or s
                    case 83:
                    case 40:
                        if(row<2){
                            fieldMatrix[row][col].setBackground(Color.BLACK);
                            row++;
                            fieldMatrix[row][col].setBackground(Color.darkGray);
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent released){
                released.consume();
            }
        };
        frame.addKeyListener(moveListener);

        KeyListener inputListener = new KeyListener(){

            @Override
            public void keyTyped(KeyEvent t){t.consume();}

            @Override
            public void keyPressed(KeyEvent input){
                int keyCode = input.getKeyCode();
                String currentText = fieldMatrix[row][col].getText();
                int length = currentText.length();
                if(48<=keyCode && keyCode<=57){
                    if(standaloneSign(keyCode-48)){
                        return;
                    }
                    matrix[row][col] = matrix[row][col] >= 0 ? appendToPositive(keyCode - 48) : appendToNegative(keyCode-48);
                    fieldMatrix[row][col].setText(String.valueOf(matrix[row][col]));
                }
                else if(97<=keyCode && keyCode<=105){
                    if(standaloneSign(keyCode-2*48)){
                        return;
                    }
                    matrix[row][col] = matrix[row][col] >= 0 ? appendToPositive(keyCode - 2*48) : appendToNegative(keyCode-2*48);
                    fieldMatrix[row][col].setText(String.valueOf(matrix[row][col]));
                }
                //enter
                else if(keyCode==10){
                    label.setText(String.valueOf(new Matrix(matrix).determinant()));
                    JOptionPane.showMessageDialog(frame,label,"RESULT", JOptionPane.PLAIN_MESSAGE);
                }
                else if(keyCode==45 && matrix[row][col]==0 && !negativesMatrix[row][col]){
                    negativesMatrix[row][col] = true;
                    fieldMatrix[row][col].setText("-");
                }
                //backspace
                else if(keyCode==8){
                    //deleting '-' sign
                    if(length == 1 && currentText.charAt(0) == '-'){
                        negativesMatrix[row][col] = false;
                    }
                    if(length==0) return;

                    matrix[row][col] /= 10;
                    fieldMatrix[row][col].setText(currentText.substring(0,length-1));
                }
                else if(keyCode==127){
                    matrix[row][col] = 0;
                    fieldMatrix[row][col].setText("");
                    negativesMatrix[row][col] = false;
                }
                Window.displayArr(matrix);
            }

            private boolean standaloneSign(int code){
                if(negativesMatrix[row][col] && matrix[row][col]==0){
                    matrix[row][col] = -code;
                    fieldMatrix[row][col].setText(String.valueOf(matrix[row][col]));
                    return true;
                }
                return false;
            }

            @Override
            public void keyReleased(KeyEvent r){r.consume();}

            private int getLength(int num){
                int length=0;
                while(num!=0){
                    length++;
                    num/=10;
                }
                return length;
            }
            private int appendToPositive(int num){
                int length = getLength(matrix[row][col]);
                int power = 1;

                while(power<=length){
                    int remainder = matrix[row][col]%10;
                    num += (int) (remainder * Math.pow(10,power++));
                    matrix[row][col]/=10;
                }
                return num;
            }
            private int appendToNegative(int num){
                //223 + 4
                //length == 3
                //10^3 = 1000
                int length = getLength(matrix[row][col]);
                int power = 1;
                num = -num;
                while(power<=length){
                    int remainder = -matrix[row][col]%10;
                    num -= (int) (remainder * Math.pow(10,power++));
                    matrix[row][col]/=10;
                }
                return num;
            }
        };
        frame.addKeyListener(inputListener);


    }
}
