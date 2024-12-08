import React, { useEffect } from "react";

export const Payment = () => {
  const APP_ID = import.meta.env.VITE_App_ID;
  const APP_KEY = import.meta.env.VITE_App_Key;

  useEffect(() => {
    // 動態加載 TapPay SDK
    const loadTapPaySDK = () => {
      const script = document.createElement("script");
      script.src = "https://js.tappaysdk.com/sdk/tpdirect/v5.14.0";
      script.async = true;
      script.onload = () => {
        // SDK 載入完成後初始化
        window.TPDirect.setupSDK(APP_ID, APP_KEY, "sandbox");

        // 設置信用卡表單
        window.TPDirect.card.setup({
          fields: {
            number: {
              element: "#card-number",
              placeholder: "**** **** **** ****",
            },
            expirationDate: {
              element: "#card-expiration-date",
              placeholder: "MM / YY",
            },
            ccv: {
              element: "#card-ccv",
              placeholder: "後三碼",
            },
          },
          styles: {
            input: {
              color: "black",
              "background-color": "#000",
            },
            ":focus": {
              color: "black",
            },
            ".valid": {
              color: "green",
            },
            ".invalid": {
              color: "red",
            },
          },
          isMaskCreditCardNumber: true,
          maskCreditCardNumberRange: {
            beginIndex: 6,
            endIndex: 11,
          },
        });

        // 監聽表單狀態變化
        window.TPDirect.card.onUpdate(function (update) {
          // 卡片資訊是否完整
          if (update.canGetPrime) {
            // 啟用提交按鈕
          } else {
            // 停用提交按鈕
          }
        });
      };
      document.body.appendChild(script);
    };

    loadTapPaySDK();

    // 清理函數
    return () => {
      const script = document.querySelector(
        'script[src="https://js.tappaysdk.com/sdk/tpdirect/v5.14.0"]'
      );
      if (script) {
        document.body.removeChild(script);
      }
    };
  }, [APP_ID, APP_KEY]);

  const handleSubmit = () => {
    // 取得 TapPay Fields 的 status
    const tappayStatus = window.TPDirect.card.getTappayFieldsStatus();

    // 確認是否可以 getPrime
    if (tappayStatus.canGetPrime === false) {
      alert("請確認信用卡資訊完整");
      return;
    }

    // Get prime
    window.TPDirect.card.getPrime((result) => {
      if (result.status !== 0) {
        alert("取得 prime 失敗");
        return;
      }
      alert("取得 prime 成功，prime: " + result.card.prime);
      // 這邊你可以送 prime 到你的後端
    });
  };

  return (
    <div style={{ padding: "16px", color: "black" , backgroundColor:"#fefae0"}}>
      <div style={{ maxWidth: "320px", margin: "0 auto" }}>
        <h2
          style={{
            fontSize: "1.25rem",
            fontWeight: "bold",
            marginBottom: "16px",
          }}
        >
          信用卡付款
        </h2>
        <div style={{ gap: "16px", display: "flex", flexDirection: "column" }}>
          <div style={{ gap: "8px", display: "flex", flexDirection: "column" }}>
            <label
              style={{
                display: "block",
                fontSize: "0.875rem",
                fontWeight: "500",
              }}
            >
              卡號
            </label>
            <div
              id="card-number"
              style={{
                height: "40px",
                borderRadius: "8px",
                border: "1px solid #D1D5DB",
                padding: "8px 12px",
              }}
            />
          </div>

          <div style={{ gap: "8px", display: "flex", flexDirection: "column" }}>
            <label
              style={{
                display: "block",
                fontSize: "0.875rem",
                fontWeight: "500",
              }}
            >
              到期日
            </label>
            <div
              id="card-expiration-date"
              style={{
                height: "40px",
                borderRadius: "8px",
                border: "1px solid #D1D5DB",
                padding: "8px 12px",
              }}
            />
          </div>

          <div style={{ gap: "8px", display: "flex", flexDirection: "column" }}>
            <label
              style={{
                display: "block",
                fontSize: "0.875rem",
                fontWeight: "500",
              }}
            >
              安全碼
            </label>
            <div
              id="card-ccv"
              style={{
                height: "40px",
                borderRadius: "8px",
                border: "1px solid #D1D5DB",
                padding: "8px 12px",
              }}
            />
          </div>

          <button
            onClick={handleSubmit}
            style={{
              width: "100%",
              backgroundColor: "#1E3A8A",
              color: "white",
              padding: "8px 16px",
              borderRadius: "8px",
              transition: "background-color 0.2s",
              cursor: "pointer",
            }}
            onMouseEnter={(e) => (e.target.style.backgroundColor = "#1D4ED8")}
            onMouseLeave={(e) => (e.target.style.backgroundColor = "#1E3A8A")}
          >
            確認付款
          </button>
        </div>
      </div>
    </div>
  );
};
