# Tutorial 所有使用指南如下
---
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
### 開發注意事項
- `PaymentController` 為 API 測試使用，請參考其邏輯進行開發者程式碼的修改。  
- `TapPayRequest` 與 `TapPayResponse` 的結構可參考官方文件進行新增與調整。  
  [TapPay 官方文件](https://docs.tappaysdk.com/tutorial/zh/back.html#pay-by-prime-api)
- 官方提供之測試卡號為 4242424242424242，有效期限 01/25，CCV碼 123。(經測試有效期限與CCV碼於測試環境 TapPay server 均無驗證)。
### 示意圖 (後端流程)
[ 前端提交資料 ] --> [ 後端接收 Prime + 訂單資料 ] --> [ 調用 TapPay Payment API ] --> [ TapPay 返回交易結果 ] --> [ 成功: 儲存至資料庫 ] or [ 失敗: 回傳錯誤訊息 ] --> [ 回傳支付處理結果 ]

---

## 使用 Redis 整合 Spring Boot Cache 教學
本教學說明如何將 Redis 整合至 Spring Boot 專案，並使用 Spring Boot 內建的 Cache 機制進行緩存操作。
### 流程說明
1. **啟用 Spring Boot Cache**  
   在您的 Spring Boot 主應用程式類別中加入 `@EnableCaching` 註解來啟用 Spring 的緩存機制。
2. **在 pom.xml 中加入 Redis 依賴**
   ```xml
   <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
5. **配置 Redis 設定**
   ```properties
   spring.redis.host=localhost
   spring.redis.port=6379
7. **模型類別必須實作 Serializable**
8. **常用的 Cache 註解**  
   @Cacheable 用來將方法結果緩存起來。如果方法的參數相同，再次調用時會直接返回緩存中的數據。 (主要 cache 邏輯)  
   @CacheEvict 用來從緩存中刪除指定的緩存數據。通常用於數據變更後更新緩存。 (更新、刪除時標註)  
   @CachePut 每次方法執行時都會更新緩存，而不管是否有命中緩存。 (更新時標註)  

