# Hướng dẫn sử dụng code từ đội tuyển FTC-24751
## Xin chào các bạn, để có thể vận hành bamboo bot của đội FTC chúng tôi, dưới đây là bản hướng dẫn đầy đủ từng bước chi tiết để:
### 1.	Lấy code từ repo GitHub
### 2.	Clone / Import thành project Android Studio
### 3.	Paste URL để đồng bộ dự án với GitHub
### 4.	Nạp code vào Control Hub của FTC
### 5.	Điều khiển robot

<img src="/images/images.jpg" width="500" />

## I.	TẢI HOẶC CLONE REPO TỪ GITHUB

### Cách 1 — Clone bằng URL (Khuyên dùng)
1.	Mở repo:
https://github.com/greenamsroboticsteam/Bamboo-bot-24751
2.	Bấm nút Code (màu xanh lá) → Copy HTTPS URL. 
3.	Mở Android Studio → Get from VCS.
4.	Paste URL → Chọn thư mục lưu → Clone.

<img src="/images/as-welcome.webp" width="500" />

________________________________________
### Cách 2 — Download ZIP
1.	Trên repo → bấm Code → Download ZIP.
2.	Giải nén file ZIP.
3.	Mở Android Studio → Open → chọn thư mục đã giải nén.
II. IMPORT DỰ ÁN FTC VÀO ANDROID STUDIO

#### Khi đã clone hoặc mở ZIP xong:
1.	Android Studio sẽ tự phát hiện Gradle và build.
2.	Chờ Gradle “Sync” hoàn tất, việc này thường tốn khoảng 15 giây, trong lúc đó bạn không nên truy cập hay chỉnh sửa các file.
3.	Kiểm tra cây thư mục xem có cấu trúc chuẩn FTC:
4.	Code robot của bạn sẽ nằm trong:
III.	Dùng Android Studio gắn dự án với Github. (không bắt buộc)
       1.  Vào File → Settings → Version Control → Git (nếu cần kiểm tra).  
2.  Vào VCS → Enable Version Control Integration → Git.
3.  Vào Git → Manage Remotes → paste URL GitHub của bạn.
4.  Commit & Push khi cần.  
IV. Thiết lập tên của robot trong Control Hub
Để có thể chạy robot dựa trên code JAVA, bạn cần có Driver Station (Driver Hub) và Control Hub (có thể bổ sung Expansion Hub nếu cần). Sau đó hãy thực hiện các bước sau:
 
1.	Đầu tiên, hãy đổi tên của Driver Station và Control Hub sao cho phù hợp với thể lệ giải đấu FTC (giả sử mã số của đội bạn là 24751 thì hãy để tên lần lượt là 24751-DS và 24751-RC), thể lệ cuộc thi chi tiết: https://ftc-resources.firstinspires.org/ftc/game/manual
2.	Sau đó hãy chọn mạng cục bộ của Control Hub (sau khi đã kích hoạt control hub) và thay đổi mật khẩu (tên mạng thường là FTC - |mã số đội|)
   
3.	Tiếp theo, hãy nhấn vào dấu “3 chấm” ở góc trên cùng bên phải và nhấn vào Configure Robot. 
  
4.	Trong phần Configure Robot hãy nhấn vào nút “new”  rồi nhấn Control Hub Portal, trong này bạn sẽ thấy tất cả các loại subsytem mà Control Hub cung cấp.  

 

 
5.	Tiếp theo, hãy chọn từng loại subsystem được sử dụng trong con bot của bạn (motor, servo, IMU,…), chọn đúng sản phẩm rồi cuối cùng hãy nhập tên của từng subsystem. Từng subsystem sẽ ứng với cổng có số tương tự trên Control Hub khi kết nối.
 
### Lưu ý: Hãy đặt tên dễ nhớ và thể hiện đúng chức năng của nó. Ngoài ra trong phần code hãy chỉnh sửa tên của từng subsystem sao cho trùng khớp với tên trong Driver Station.
6.	Sau khi đã thực hiện xong các bước trên, hãy nhấn done và save để lưu lại dữ liệu trong Driver Station. Sau khi nhấn save bạn sẽ được yêu cầu nhập tên cho Configure của bạn, phần này bạn có thể đặt tên một cách tùy ý.  
7.	Để có thể sử dụng phần Configure bạn đã làm phía trên, hãy nhấn vào nút activate dưới tên của nó. Còn nếu bạn muốn chỉnh sửa hãy nhấn vào nút edit và xóa ở nút Delete.
  
8.	Sau khi nhấn Activate, bạn nhấn nút mũi tên hướng ra ngoài và bạn sẽ thấy dòng chữ restarting robot, hãy đợi vài giây để Driver Station thực hiện tác vụ.
Bạn có thể tham khảo hướng dẫn chi tiết từ video sau: 
https://www.youtube.com/watch?v=x2Gi-i6Z2pI

## II. IMPORT DỰ ÁN FTC VÀO ANDROID STUDIO


### Khi đã clone hoặc mở ZIP xong:
1.	Android Studio sẽ tự phát hiện Gradle và build.
2.	Chờ Gradle “Sync” hoàn tất, việc này thường tốn khoảng 15 giây, trong lúc đó bạn không nên truy cập hay chỉnh sửa các file.
3.	Kiểm tra cây thư mục xem có cấu trúc chuẩn FTC:
4.	Code robot của bạn sẽ nằm trong:
## III.	Dùng Android Studio gắn dự án với Github. (không bắt buộc)
1.  Vào File → Settings → Version Control → Git (nếu cần kiểm tra).  

<img src="/images/Screenshot 2025-12-07 104645.png" width="500" />

2.  Vào VCS → Enable Version Control Integration → Git.
3.  Vào Git → Manage Remotes → paste URL GitHub của bạn.
4.  Commit & Push khi cần.  

<img src="/images/Screenshot 2025-12-07 105217.png" width="500" />


## IV. Thiết lập tên của robot trong Control Hub
### Để có thể chạy robot dựa trên code JAVA, bạn cần có Driver Station (Driver Hub) và Control Hub (có thể bổ sung Expansion Hub nếu cần). Sau đó hãy thực hiện các bước sau:
 
1.  Đầu tiên, hãy đổi tên của Driver Station và Control Hub sao cho phù hợp với thể lệ giải đấu FTC (giả sử mã số của đội bạn là 24751 thì hãy để tên lần lượt là 24751-DS và 24751-RC), thể lệ cuộc thi chi tiết: https://ftc-resources.firstinspires.org/ftc/game/manual
2.	Sau đó hãy chọn mạng cục bộ của Control Hub (sau khi đã kích hoạt control hub) và thay đổi mật khẩu (tên mạng thường là FTC - |mã số đội|)


<img src="/images/Screenshot 2025-12-23 153333.png" width="300" />


<img src="/images/Screenshot 2025-12-23 153350.png" width="300" />

   
3.	Tiếp theo, hãy nhấn vào dấu “3 chấm” ở góc trên cùng bên phải và nhấn vào Configure Robot. 


<img src="/images/Screenshot 2025-12-23 153411.png" width="300" />


<img src="/images/Screenshot 2025-12-23 153427.png" width="300" />

  
4.	Trong phần Configure Robot hãy nhấn vào nút “new”  rồi nhấn Control Hub Portal, trong này bạn sẽ thấy tất cả các loại subsytem mà Control Hub cung cấp.  


<img src="/images/Screenshot 2025-12-23 154809.png" width="400" />



<img src="/images/Screenshot 2025-12-23 152318.png" width="400" />



<img src="/images/Screenshot 2025-12-23 152335.png" width="400" />

 
5.	Tiếp theo, hãy chọn từng loại subsystem được sử dụng trong con bot của bạn (motor, servo, IMU,…), chọn đúng sản phẩm rồi cuối cùng hãy nhập tên của từng subsystem. Từng subsystem sẽ ứng với cổng có số tương tự trên Control Hub khi kết nối.


<img src="/images/Screenshot 2025-12-23 152359.png" width="500" />

 
### Lưu ý: Hãy đặt tên dễ nhớ và thể hiện đúng chức năng của nó. Ngoài ra trong phần code hãy chỉnh sửa tên của từng subsystem sao cho trùng khớp với tên trong Driver Station.
6.	Sau khi đã thực hiện xong các bước trên, hãy nhấn done và save để lưu lại dữ liệu trong Driver Station. Sau khi nhấn save bạn sẽ được yêu cầu nhập tên cho Configure của bạn, phần này bạn có thể đặt tên một cách tùy ý.  


<img src="/images/Screenshot 2025-12-23 155150.png" width="500" />


7.	Để có thể sử dụng phần Configure bạn đã làm phía trên, hãy nhấn vào nút activate dưới tên của nó. Còn nếu bạn muốn chỉnh sửa hãy nhấn vào nút edit và xóa ở nút Delete.


<img src="/images/Screenshot 2025-12-23 155202.png" width="500" />

  
8.	Sau khi nhấn Activate, bạn nhấn nút mũi tên hướng ra ngoài và bạn sẽ thấy dòng chữ restarting robot, hãy đợi vài giây để Driver Station thực hiện tác vụ.

### Bạn có thể tham khảo hướng dẫn chi tiết từ video sau: https://www.youtube.com/watch?v=x2Gi-i6Z2pI


## V. NẠP CODE VÀO CONTROL HUB / ROBOT CONTROLLER
### Cách A — USB vào Control Hub
1.	Nối cổng USB – A từ máy tính có chứa mã code với cổng USB C phía trên Control Hub. Sau đó máy tính sẽ nhận kết nối và hiển thị dòng chữ Control Hub connected góc dưới bên phải.
2.	Android Studio nhận thiết bị như 1 máy Android.
3.	Để nạp code vào control hub, hãy nhấn vào nút mũi tên trên cùng của giao diện android studio

<img src="/images/Screenshot 2025-12-06 105026.png" width="500" />



<img src="/images/Screenshot 2025-12-06 105207.png" width="500" />

  
4.	Sau đó hãy chờ khoảng 30 giây để code được nạp vào control hub
### Cách B — Dùng điện thoại Android làm Robot Controller
1.	Cài app FTC Robot Controller từ SDK (hoặc từ Google Play nếu hợp lệ).
2.	Cắm điện thoại vào PC qua USB.
3.	Nạp code tương tự như cách A
### Chú ý: Nếu bạn chọn cách B, hãy chắc chắn rằng điện thoại của bạn chạy bằng hệ điều hành Android. Ngoài ra, hầu hết mọi điện thoại đang được sử dụng ngày nay đều không được mang thi đấu chính thức, vậy nên cách thuận tiên nhất vẫn là cách A.
## VI. KIỂM TRA CODE TRONG DRIVER STATION
1.  Bật Control Hub.
2.  Bật điện thoại hoặc Driver Station.
3.  Chờ kết nối WiFi giữa DS ↔ Control Hub.
4.  Trong Driver Station → tab OpModes bạn sẽ thấy các OpMode bạn đã viết (ví dụ: TeleOp cho điều khiển thủ công, Autonomous cho phần chạy tự động).
5.  Chọn OpMode → bấm INIT → khi sẵn sàng bấm START.
6.  Robot sẽ chạy đúng code bạn đã nạp. 
7.  Nếu muốn dừng toàn bộ hoạt động của Robot hãy nhấn một lần nữa vào nút hình vuông trên DS (cùng vị tri với INIT và START).  


<img src="/images/Screenshot 2025-12-23 160036.png" width="500" />


## VII. Các subsytem và TeleOp cần thiết để thao tác trên bamboo bot của chúng tôi.

### Chúng tôi đã tích hợp nhiều file và thư mục giúp các bạn có thể dễ dàng điều khiển cũng như gỡ lỗi hay chỉnh sửa một vài cơ chế của bamboo bot như sau:

1. Drivebase và Drivebase_Test: Chỉ bao gồm phần điều khiển di chuyển của bot (hay còn gọi là drivebase).
2. Flywheel và Flywheel_Test: Chỉ bao gồm phần điều khiển cơ chế bắn bóng (flywheel).
3. Motor_Basic và Motor_Test: Điều khiển cơ bản một motor.
4. Servo_Basic và Servo_Test: Điều khiển cơ bản một servo.
5. starterbot: Code điều khiển chính của cả bamboo bot, các nút điều khiển (trên gamepad) bao gồm:
- right bumper: cơ chế ăn bóng (intake), nhấn một lần để ăn bóng, nhấn thêm lần nữa để dừng intake;
- right trigger: cơ chế nhả bóng (khiến intake quay ngược chiều), nhấn giữ để nhả bóng, thả tay để dừng intake;
- dpad up (nút nhấn lên) : cơ chế bắn bóng ở cự ly gần (Power = 1.0), chỉ cần nhấn 1 lần;
- dpad down (nút nhấn xuống) : cơ chế bắn bóng ở cự ly xa (Power = 1.0), chỉ cần nhấn 1 lần;
- Nút hình vuông (gamepad.x) : nhấn giữ để mở chặn bóng và đẩy bóng lên flywheel;
- tay cầm phải (right_stick) : điều khiển tiến và lùi;
- tay cầm trái (left_stick) : điều khiển bot xoay;

## VIII. Cách điều khiển bamboo bot.
### Sau khi bạn đã nhấn init và start hãy thực hiện các bước sau để điều khiển bamboo bot:
- Để di chuyển, hãy gạt cần điều khiển bên phải (right_stick) lên và xuống điều điều khiển robot tiến và lùi, và gạt cần bên trái (left_stick) sang trái và phải để xoay bot.
- Nhấn nút left_bumper để bật / tắt flywheel, và nhấn nút lên xuống (dpad up / down) để tăng / giảm tốc độ flywheel.
- Nhấn nút right_bumper để bật / tắt intake (ăn bóng), và nhấn giữ nút right_trigger để bật chế độ nhả bóng (xoay ngược intake).
- Khi bạn muốn đẩy bóng lên flywheel, hãy tắt intake trước (bằng cách nhấn nút right_bumper) rồi nhấn giữ nút vuông ở bên trái tay cầm để vừa bật feed bóng và vừa mở block (chặn bóng).

### Ngoài ra, trong phần thi Automatic của FTC, bạn chỉ sử dụng file code có tên AutoOp.java và bot sẽ tự chạy trên một đường nhất định.
                                   --- The end ---

