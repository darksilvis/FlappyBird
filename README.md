FlappyBird oyunun kodunu yazdım. Bu oyunu yaparken divide and conquer (böl ve fethet) yöntemini kullandım. 
İlk başta basit bir algoritmayla ilerledim. Sonrasında daha gerçekçi olması için kullanıcı bilgilerini kaydeden ve kullanıcıların oyunu kaç defa oynadığını maximum skorunun ne olduğunu kaydeden bir 
algoritma geliştirdim.Sonrasında bunu bir adım daha öteye taşıyarak oyuna giren kullanıcıların kullanıcı adları aynıysa  Bu kullanıcı adı zaten kullanıldı şeklinde hata kodu yazdım.
sonrasında giren kullanıcıların maximum skoruna göre bir sıralama yaptırdım. Tabi bunları yaparken uygulama sonlanınca veriler kaybolmasın diye verileri Json kutuphanenesine kaydettim 
Bu sayede uygulama yeniden başladığı zaman kullanıcıların verileri kaldığı yerden devam etmiş oldu. FlappyBird oyun kodunu kısaca bu şekilde açıklayabilirim
