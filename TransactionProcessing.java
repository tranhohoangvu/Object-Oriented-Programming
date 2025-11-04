import java.util.*;
import java.io.*;

public class TransactionProcessing
{
    private ArrayList<Payment> paymentObjects;  // Danh sach chua cac tai khoan thanh toan
    private IDCardManagement idcm;  // Danh sach chua cac the dinh danh
    
    public TransactionProcessing(String idCardPath, String paymentPath)
    {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);
    }

    public ArrayList<Payment> getPaymentObject()
    {
        return this.paymentObjects;
    }

    // Requirement 3
    public boolean readPaymentObject(String path)
    {
        // Tạo một danh sách mới để lưu trữ các đối tượng thanh toán
        paymentObjects = new ArrayList<Payment>();

        try
        {
            // Đọc file từ đường dẫn được truyền vào qua tham số path
            File f = new File(path);
            Scanner sc = new Scanner(f);

            // Lặp qua từng dòng trong tệp
            while (sc.hasNextLine())
            {
                // Với mỗi dòng trong file, tách chuỗi thành các phần tử dựa vào dấu phẩy
                String dong = sc.nextLine();
                String[] t = dong.split(",");
                
                if (t.length == 2)
                {
                    if (t[0].length() == 6)
                    {
                        int element = Integer.parseInt(t[0]);
                        double tilelaisuat = Double.parseDouble(t[1]);
                        // Tạo một đối tượng BankAccount từ số tài khoản và tỷ lệ lãi suất
                        Payment pm = new BankAccount(element, tilelaisuat);
                        // Thêm đối tượng mới vào danh sách paymentObjects
                        paymentObjects.add(pm);
                    }
                }

                if (t.length == 1)
                {
                    if (t[0].length() == 6)
                    {
                        int element = Integer.parseInt(t[0]);
                        // Lặp qua từng phần tử trong danh sách thẻ (IDCard)
                        for (IDCard sodinhdanh : idcm.getIDCards())
                        {
                            // Kiểm tra xem số định danh của thẻ có trùng với phần tử đang xét không
                            if (sodinhdanh.getSodinhdanh() == element)
                            {
                                try
                                {
                                    // Tạo đối tượng IDCard mới từ IDCard đã có
                                    IDCard idc = new IDCard(sodinhdanh.getSodinhdanh(), sodinhdanh.getHoten(), sodinhdanh.getGioitinh(),
                                                            sodinhdanh.getNgaysinh(), sodinhdanh.getDiachi(), sodinhdanh.getSodienthoai());
                                    // Tạo một đối tượng ConvenientCard mới từ IDCard vừa tạo
                                    Payment pm = new ConvenientCard(idc);
                                    // Thêm đối tượng mới vào danh sách paymentObjects
                                    paymentObjects.add(pm);
                                } catch (Exception e)
                                {
                                    // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                                    e.printStackTrace();                                
                                }
                            }
                        }
                    }
                }

                if (t.length == 1)
                {
                    if (t[0].length() == 7)
                    {
                        int element = Integer.parseInt(t[0]);
                        // Tạo một đối tượng EWallet mới từ số điện thoại
                        Payment newPayment = new EWallet(element);
                        // Thêm đối tượng mới vào danh sách paymentObjects
                        paymentObjects.add(newPayment);
                    }
                }
            }
            sc.close();
            // Đọc file thành công
            return true;
        } catch (Exception e)
        {
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
            e.printStackTrace();
            // Đọc file thất bại
            return false; 
        }
    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards()
    {
        // Tạo một danh sách mới để lưu trữ các thẻ tiện lợi "Adult"
        ArrayList<ConvenientCard> adc = new ArrayList<>();

        // Duyệt qua danh sách paymentObjects
        for (Payment pm : paymentObjects)
        {
            // Kiểm tra xem phần tử có phải là một thẻ tiện lợi ConvenientCard không
            if (pm instanceof ConvenientCard)
            {
                try
                {
                    // Ép kiểu phần tử thành ConvenientCard
                    ConvenientCard cvc = (ConvenientCard) pm;
                    // Kiểm tra xem loại thẻ có phải là "Adult" không
                    if (cvc.getType().equals("Adult"))
                    {
                        // Thêm vào danh sách adc
                        adc.add(cvc);
                    }
                } catch (Exception e)
                {
                    // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                    e.printStackTrace();
                }
            }
        }
        return adc;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth()
    {
        // Tạo một danh sách mới để lưu trữ các thẻ định danh sở hữu cả 3 loại tài khoản thanh toán
        ArrayList<IDCard> chb = new ArrayList<>();

        // Tìm danh sách EWallet
        // Tạo một danh sách mới để lưu trữ các tài khoản EWallet
        ArrayList<EWallet> ewal = new ArrayList<>();
        // Duyệt qua danh sách paymentObjects 
        for (Payment pm1 : paymentObjects)
        {
            // Kiểm tra xem phần tử có phải là EWallet không
            if (pm1 instanceof EWallet)
            {
                // Ép kiểu phần tử thành EWallet
                EWallet ewl = (EWallet) pm1;
                // Thêm vào danh sách EWallet
                ewal.add(ewl);
            }
        }

        // Tìm danh sách BankAccount
        // Tạo một danh sách mới để lưu trữ các tài khoản BankAccount
        ArrayList<BankAccount> bank = new ArrayList<>();
        // Duyệt qua danh sách paymentObjects
        for (Payment pm2 : paymentObjects)
        {
            // Kiểm tra xem phần tử có phải là BankAccount không
            if (pm2 instanceof BankAccount)
            {
                // Ép kiểu phần tử thành BankAccount
                BankAccount baa = (BankAccount) pm2;
                // Thêm vào danh sách BankAccount
                bank.add(baa);
            }
        }

        // Tìm danh sách ConvenientCard
        // Tạo một danh sách mới để lưu trữ các tài khoản ConvenientCard
        ArrayList<ConvenientCard> conv = new ArrayList<>();
        // Duyệt qua danh sách paymentObjects
        for (Payment pm3 : paymentObjects)
        {
            // Kiểm tra xem phần tử có phải là ConvenientCard không
            if (pm3 instanceof ConvenientCard)
            {
                try
                {
                    // Ép kiểu phần tử thành ConvenientCard
                    ConvenientCard cc = (ConvenientCard) pm3;
                    // Thêm vào danh sách ConvenientCard
                    conv.add(cc);
                } catch (Exception e)
                {
                    // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                    e.printStackTrace();
                }
            }
        }

        // Duyệt qua danh sách ConvenientCard và kiểm tra điều kiện
        for (ConvenientCard cvc : conv)
        {
            // Lấy thông tin IDCard từ ConvenientCard
            IDCard idc1 = cvc.getThedinhdanh();

            // Kiểm tra IDCard có tồn tại trong danh sách EWallet
            boolean isEWallets = false;

            // Duyệt qua từng phần tử trong danh sách EWallet
            for (EWallet ew : ewal)
            {
                // So sánh số điện thoại của IDCard và EWallet
                if (idc1.getSodienthoai() == ew.getSodienthoai())
                {
                    // Nếu trùng thì đánh dấu là có tồn tại trong danh sách EWallet
                    isEWallets = true;
                    break;
                }
            }

            // Kiểm tra IDCard có tồn tại trong danh sách BankAccount
            boolean isBankAccounts = false;

            // Duyệt qua từng phần tử trong danh sách BankAccount
            for (BankAccount ba : bank)
            {
                // So sánh số định danh của IDCard với số tài khoản EWallet
                if (idc1.getSodinhdanh() == ba.getSotaikhoan())
                {
                    // Nếu trùng thì đánh dấu là có tồn tại trong danh sách BankAccount
                    isBankAccounts = true;
                    break;
                }
            }

            // Nếu tồn tại cả trong EWallet và BankAccount
            if (isBankAccounts && isEWallets)
            {
                //Thêm IDCard vào danh sách kết quả
                chb.add(idc1);
            }
        }
        return chb;
    }

    // Requirement 6
    public void processTopUp(String path)
    {
        try
        {
            // Đọc file từ đường dẫn được truyền vào qua tham số path
            File f = new File(path);
            Scanner sc = new Scanner(f);
            
            // Duyệt qua từng dòng trong file
            while (sc.hasNextLine())
            {
                // Với mỗi dòng trong file, tách chuỗi thành các phần tử dựa vào dấu phẩy
                String dong = sc.nextLine();
                String[] t = dong.split(",");
                
                if (t.length == 3)
                {
                    String maloaitaikhoan = t[0];
                    int thongtintaikhoan = Integer.parseInt(t[1]);
                    double sotiencannap = Double.parseDouble(t[2]);
                    
                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm1 : paymentObjects)
                    {       
                        // Kiểm tra xem phần tử có phải là EWallet không
                        if (pm1 instanceof EWallet)
                        {
                            if (maloaitaikhoan.equals("EW"))
                            {
                                // Ép kiểu phần tử thành EWallet
                                EWallet ew = (EWallet) pm1;
                                // Kiểm tra số điện thoại của EWallet có trùng với thông tin tài khoản được truyền vào hay không
                                if (ew.getSodienthoai() == thongtintaikhoan)
                                {
                                    // Nếu trùng thì nạp tiền cho EWallet
                                    ew.topUp(sotiencannap);
                                }
                            }
                        }
                    }

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm2 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là BankAccount không
                        if (pm2 instanceof BankAccount)
                        {
                            if (maloaitaikhoan.equals("BA"))
                            {
                                // Ép kiểu phần tử thành BankAccount
                                BankAccount ba = (BankAccount) pm2;
                                // Kiểm tra số tài khoản của BankAccount có trùng với thông tin tài khoản được truyền vào hay không
                                if (ba.getSotaikhoan() == thongtintaikhoan)
                                {
                                    // Nếu trùng thì nạp tiền cho BankAccount
                                    ba.napTien(sotiencannap);
                                }
                            }
                        }
                    }

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm3 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là CovenientCard không
                        if (pm3 instanceof ConvenientCard)
                        {
                            if (maloaitaikhoan.equals("CC"))
                            {
                                try
                                {
                                    // Ép kiểu phần tử thành ConvenientCard
                                    ConvenientCard cvc = (ConvenientCard) pm3;
                                    // Kiểm tra số định danh của ConvenientCard có trùng với thông tin tài khoản được truyền vào hay không
                                    if (cvc.getThedinhdanh().getSodinhdanh() == thongtintaikhoan)
                                    {
                                        // Nếu trùng thì nạp tiền cho ConvenientCard
                                        cvc.topUp(sotiencannap);
                                    }
                                } catch (Exception e)
                                {
                                    // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (Exception e)
        {
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình đọc file
            e.printStackTrace();
        }
    }

    // Requirement 7
    public ArrayList<Bill> getUnsuccessfulTransactions(String path)
    {
        // Khởi tạo danh sách kết quả chứa các hóa đơn thanh toán không thành công
        ArrayList<Bill> unTrans = new ArrayList<>();
    
        try
        {
            // Đọc file từ đường dẫn được truyền vào qua tham số path
            File f = new File(path);
            Scanner sc = new Scanner(f);
    
            // Duyệt qua từng dòng trong file
            while (sc.hasNextLine())
            {
                // Với mỗi dòng trong file, tách chuỗi thành các phần tử dựa vào dấu phẩy
                String dong = sc.nextLine();
                String[] t = dong.split(",");
    
                if (t.length == 5)
                {
                    int mahoadon = Integer.parseInt(t[0]);
                    double tongtien = Double.parseDouble(t[1]);
                    String mucdichchitra = t[2];
                    String maloaitaikhoan = t[3];
                    int thongtintaikhoan = Integer.parseInt(t[4]);
    
                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm1 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là EWallet không
                        if (pm1 instanceof EWallet)
                        {
                            if (maloaitaikhoan.equals("EW"))
                            {
                                // Ép kiểu phần tử thành EWallet
                                EWallet ew = (EWallet) pm1;
                                // Kiểm tra số điện thoại của EWallet có trùng với thông tin tài khoản được truyền vào hay không
                                if (ew.getSodienthoai() == thongtintaikhoan)
                                {
                                    // Nếu thanh toán không thành công
                                    if (!ew.pay(tongtien))
                                    {
                                        // Tạo mội đối tượng Bill mới
                                        Bill hoadonfail = new Bill(mahoadon, tongtien, mucdichchitra);
                                        // Thêm đối tượng Bill mới vào danh sách
                                        unTrans.add(hoadonfail);
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm2 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là BankAccount không
                        if (pm2 instanceof BankAccount)
                        {
                            if (maloaitaikhoan.equals("BA"))
                            {
                                // Ép kiểu phần tử thành BankAccount
                                BankAccount ba = (BankAccount) pm2;
                                // Kiểm tra số tài khoản của BankAccount có trùng với thông tin tài khoản được truyền vào hay không
                                if (ba.getSotaikhoan() == thongtintaikhoan)
                                {
                                    // Nếu thanh toán không thành công
                                    if (!ba.pay(tongtien))
                                    {
                                        // Tạo mội đối tượng Bill mới
                                        Bill hoadonfail = new Bill(mahoadon, tongtien, mucdichchitra);
                                        // Thêm đối tượng Bill mới vào danh sách 
                                        unTrans.add(hoadonfail);
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm3 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là CovenientCard không
                        if (pm3 instanceof ConvenientCard)
                        {
                            if (maloaitaikhoan.equals("CC"))
                            {
                                try
                                {
                                    // Ép kiểu phần tử thành ConvenientCard
                                    ConvenientCard cvc = (ConvenientCard) pm3;
                                    // Kiểm tra số định danh của ConvenientCard có trùng với thông tin tài khoản được truyền vào hay không
                                    if (cvc.getThedinhdanh().getSodinhdanh() == thongtintaikhoan)
                                    {
                                        // Nếu thanh toán không thành công
                                        if (!cvc.pay(tongtien))
                                        {
                                            // Tạo mội đối tượng Bill mới
                                            Bill hoadonfail = new Bill(mahoadon, tongtien, mucdichchitra);
                                            // Thêm đối tượng Bill mới vào danh sách 
                                            unTrans.add(hoadonfail);
                                        }
                                        break;
                                    }
                                } catch (Exception e)
                                {
                                    // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (Exception e)
        {
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình đọc file
            e.printStackTrace();
        }
        return unTrans;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path)
    {
        // Khởi tạo danh sách kết quả chứa các tài khoản ngân hàng có số tiền lớn nhất
        ArrayList<BankAccount> largest = new ArrayList<>();
        double max = 0;
    
        try
        {
            // Đọc file từ đường dẫn được truyền vào qua tham số path
            File f = new File(path);
            Scanner sc = new Scanner(f);
    
            // Duyệt qua từng dòng trong file
            while (sc.hasNextLine())
            {
                // Với mỗi dòng trong file, tách chuỗi thành các phần tử dựa vào dấu phẩy
                String dong = sc.nextLine();
                String[] t = dong.split(",");
    
                if (t.length == 5)
                {
                    double tongtien = Double.parseDouble(t[1]);
                    String maloaitaikhoan = t[3];
                    int thongtintaikhoan = Integer.parseInt(t[4]);
    
                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là BankAccount không
                        if (maloaitaikhoan.equals("BA"))
                        {
                            if (pm instanceof BankAccount)
                            {
                                // Ép kiểu phần tử thành BankAccount
                                BankAccount ba = (BankAccount) pm;
                                // Kiểm tra số tài khoản của BankAccount có trùng với thông tin tài khoản được truyền vào hay không
                                if (ba.getSotaikhoan() == thongtintaikhoan)
                                {
                                    // Nếu thanh toán thành công
                                    if (ba.pay(tongtien))
                                    {
                                        // Kiểm tra xem tổng tiền có lớn hơn max hay không
                                        if (tongtien > max)
                                        {
                                            max = tongtien;
                                            // Xóa các phần tử cũ trong danh sách kết quả
                                            largest.clear();
                                            // Thêm phần tử mới vào danh sách kết quả
                                            largest.add(ba);
                                        }
                                        else if (tongtien == max)
                                        {
                                            // Thêm phần tử mới vào danh sách kết quả
                                            largest.add(ba);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (Exception e)
        {
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình đọc file
            e.printStackTrace();
        }
        return largest;
    }
    
    //Requirement 9
    public void processTransactionWithDiscount(String path)
    {
        try
        {
            // Đọc file từ đường dẫn được truyền vào qua tham số path
            File f = new File(path);
            Scanner sc = new Scanner(f);
    
            // Duyệt qua từng dòng trong file
            while (sc.hasNextLine())
            {
                // Với mỗi dòng trong file, tách chuỗi thành các phần tử dựa vào dấu phẩy
                String dong = sc.nextLine();
                String[] t = dong.split(",");
    
                if (t.length == 5)
                {
                    double tongtien = Double.parseDouble(t[1]);
                    String mucdichchitra = t[2];
                    String maloaitaikhoan = t[3];
                    int thongtintaikhoan = Integer.parseInt(t[4]);

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm1 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là BankAccount không
                        if (pm1 instanceof BankAccount)
                        {
                            if (maloaitaikhoan.equals("BA"))
                            {
                                // Ép kiểu phần tử thành BankAccount
                                BankAccount ba = (BankAccount) pm1;
                                // Kiểm tra số tài khoản của BankAccount có trùng với thông tin tài khoản được truyền vào hay không
                                if (ba.getSotaikhoan() == thongtintaikhoan)
                                {
                                    // Gọi phương thức thanh toán của BankAccount
                                    ba.pay(tongtien);
                                }
                            }
                        }
                    }

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm2 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là EWallet không
                        if (pm2 instanceof EWallet)
                        {
                            if (maloaitaikhoan.equals("EW"))
                            {
                                // Ép kiểu phần tử thành EWallet
                                EWallet ew1 = (EWallet) pm2;
                                // Kiểm tra số điện thoại của EWallet có trùng với thông tin tài khoản được truyền vào hay không
                                if (ew1.getSodienthoai() == thongtintaikhoan)
                                {
                                    // Kiểm tra xem mục đích chi trả có phải là Clothing hay không
                                    if (mucdichchitra.equals("Clothing"))
                                    {
                                        // Kiểm tra xem tổng tiền có lớn hơn 500 hay không
                                        if (tongtien > 500)
                                        {
                                            // Duyệt qua danh sách paymentObjects
                                            for (Payment pm3 : paymentObjects)
                                            {
                                                // Kiểm tra xem phần tử có phải là ConvenientCard không
                                                if (pm3 instanceof ConvenientCard)
                                                {
                                                    // Ép kiểu phần tử thành ConvenientCard
                                                    ConvenientCard cvc1 = (ConvenientCard) pm3;
                                                    try
                                                    {
                                                        // Kiểm tra số điện thoại của cvc1 có trùng với số điện thoại của ew1 hay không
                                                        if (cvc1.getThedinhdanh().getSodienthoai() == ew1.getSodienthoai())
                                                        {
                                                            // Gán makhachhang1 bằng thẻ dữ liệu của ConvenientCard
                                                            IDCard makhachhang1 = cvc1.getThedinhdanh();
                                                            // Kiểm tra điều kiện giới tính và tuổi của khách hàng
                                                            if (((makhachhang1.getGioitinh().equals("Female")) && (makhachhang1.Tinhtuoi() < 18)) 
                                                                ||  ((makhachhang1.getGioitinh().equals("Male")) && (makhachhang1.Tinhtuoi() < 20)))
                                                            {
                                                                tongtien = tongtien - tongtien * 0.15;
                                                            }
                                                        }
                                                    } catch (Exception e)
                                                    {
                                                        // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    // Gọi phương thức thanh toán của EWallet
                                    ew1.pay(tongtien);
                                }
                            }
                        }
                    }

                    // Duyệt qua danh sách paymentObjects
                    for (Payment pm4 : paymentObjects)
                    {
                        // Kiểm tra xem phần tử có phải là ConvenientCard không
                        if (pm4 instanceof ConvenientCard)
                        {
                            if (maloaitaikhoan.equals("CC"))
                            {
                                try
                                {
                                    // Ép kiểu phần tử thành ConvenientCard
                                    ConvenientCard cvc2 = (ConvenientCard) pm4;
                                    // Kiểm tra số định danh của ConvenientCard có trùng với thông tin tài khoản được truyền vào hay không
                                    if (cvc2.getThedinhdanh().getSodinhdanh() == thongtintaikhoan)
                                    {
                                        // Gán makhachhang2 bằng thẻ dữ liệu của ConvenientCard
                                        IDCard makhachhang2 = cvc2.getThedinhdanh();
                                        // Kiểm tra điều kiện giới tính và tuổi của khách hàng
                                        if (((makhachhang2.getGioitinh().equals("Female")) && (makhachhang2.Tinhtuoi() < 18)) 
                                            ||  ((makhachhang2.getGioitinh().equals("Male")) && (makhachhang2.Tinhtuoi() < 20)))
                                        {
                                            // Duyệt qua danh sách paymentObjects
                                            for (Payment pm5 : paymentObjects)
                                            {
                                                // Kiểm tra xem phần tử có phải là EWallet không
                                                if (pm5 instanceof EWallet)
                                                {
                                                    if (maloaitaikhoan.equals("EW"))
                                                    {
                                                        // Ép kiểu phần tử thành EWallet
                                                        EWallet ew2 = (EWallet) pm5;
                                                        // Kiểm tra số điện thoại của makhachhang2 có trùng với số điện thoại của ew2 không
                                                        if (makhachhang2.getSodienthoai() == ew2.getSodienthoai())
                                                        {
                                                            // Duyệt qua danh sách paymentObjects
                                                            for (Payment pm6 : paymentObjects)
                                                            {
                                                                // Kiểm tra xem mục đích chi trả có phải là Clothing hay không
                                                                if (mucdichchitra.equals("Clothing"))
                                                                {
                                                                    // Kiểm tra xem tổng tiền có lớn hơn 500 hay không
                                                                    if (tongtien > 500)
                                                                    {
                                                                        // Ép kiểu phần tử thành IDCard
                                                                        IDCard makhachhang3 = (IDCard) pm6;
                                                                        // Kiểm tra số điện thoại của makhachhang3 có trùng với số điện thoại của ew2 hay không
                                                                        if (makhachhang3.getSodienthoai() == ew2.getSodienthoai())
                                                                        {
                                                                            // Kiểm tra xem số định danh của makchhang3 có trùng với số định danh của makhachhang2 hay không
                                                                            if (makhachhang3.getSodinhdanh() == makhachhang2.getSodinhdanh())
                                                                            {
                                                                                tongtien = tongtien - tongtien * 0.15;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        // Gọi phương thức thanh toán của ConvenientCard
                                        cvc2.pay(tongtien);
                                    }
                                } catch (Exception e)
                                {
                                    // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình ép kiểu
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (Exception e)
        {
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình đọc file
            e.printStackTrace();
        }
    }
}