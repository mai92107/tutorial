#Tutorial所有使用指南如下

## TapPay 測試環境串接 (前端：React + Vite、後端：Java)

此專案成功串接 TapPay 測試環境，實現完整的信用卡支付流程。  
前端使用 **React + Vite** 框架開發，後端採用 **Java** 開發，以下為測試環境的執行流程。

### 流程說明

1. **輸入信用卡資訊**  
   使用者於前端頁面輸入符合規範的信用卡資訊（如卡號、到期日及安全碼）。

2. **取得 Prime**  
   前端將卡片資訊透過 **TapPay SDK** 向 **TapPay API** 發送請求，取得一組唯一且有時效的交易代碼 **Prime**。

3. **傳送至後端**  
   前端將 **Prime** 及訂單資料封裝後傳送至後端服務。

4. **後端呼叫 TapPay Payment API**  
   後端調用 `PaymentService`，依照 DTO 中的 `TapPayRequest` 格式，  
   將 **Prime** 及訂單資料傳送至 **TapPay Payment API**，並接收支付結果（`TapPayResponse`）。

5. **儲存交易資料**  
   根據支付結果，後端決定是否將用戶的訂單與支付資料儲存至資料庫，完成訂單處理。

---

### 開發注意事項

- `PaymentController` 為 API 測試使用，請參考其邏輯進行開發者程式碼的修改。  
- `TapPayRequest` 與 `TapPayResponse` 的結構可參考官方文件進行新增與調整。  
  [TapPay 官方文件](https://docs.tappaysdk.com/tutorial/zh/back.html#pay-by-prime-api)

---

### 示意圖 (後端流程)

[ 前端提交資料 ] --> [ 後端接收 Prime + 訂單資料 ] --> [ 調用 TapPay Payment API ] --> [ TapPay 返回交易結果 ]
                                                                                          |
                                                                                          v
                                                                     [ 成功: 儲存至資料庫 ] or [ 失敗: 回傳錯誤訊息 ]
                                                                                          |
                                                                                          v
                                                                                 [ 回傳支付處理結果 ]
