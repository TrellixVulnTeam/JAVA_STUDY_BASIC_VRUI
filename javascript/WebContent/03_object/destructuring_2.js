/*

  ECAMA6 - 객체 추가기능 3

  디스트럭팅 ( Destructuring )
    - 객체에 입력된 값을 개별적인 변수에 할당을 편리하게  처리


*/

var arr1 = [1,2,3];
var a = arr1[0];
var b = arr1[1];
var c = arr1[2];

console.log(a,b,c);


// es6 - 위치를 기준으로 값을 추출
let arr2 = [10,20,30];

[a ,b ,c] = arr2;

console.log(arr2);
console.log(a,b,c); // a=10, b=20, c=30


[a ,b ,c] = [1, 2];
console.log(a,b,c); // a=1, b=2, c=undefined


[a, b] = arr2;
console.log(a,b); // a=10, b=20


[a , ,c] = [100, 200, 300];
console.log(a, c); // a= 100 , c= 200


[a , b = 1, c = 2] = [100, 200];
console.log(a, b, c); // a= 100 , b= 200, c=2
