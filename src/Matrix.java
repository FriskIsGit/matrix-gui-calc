class Matrix{
    int [][] arr;
    Matrix(int [][] arr){
        this.arr = arr;
    }
    int determinant(){
        int res1 = arr[0][0]*(arr[1][1]*arr[2][2]-arr[1][2]*arr[2][1]);
        int res2 = arr[0][1]*(arr[1][0]*arr[2][2]-arr[1][2]*arr[2][0]);
        int res3 = arr[0][2]*(arr[1][0]*arr[2][1]-arr[1][1]*arr[2][0]);
        return res1 - res2 + res3;
    }
}
