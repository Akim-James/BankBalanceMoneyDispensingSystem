SELECT CONCAT(c.title, ' ', c.name, ' ', c.surname)                                                    AS client_name,
       IFNULL(loan_account.loan_balance, 0)                                                            AS loan_balance,
       IFNULL(transactional_account.transactional_balance, 0)                                          AS transactional_balance,
       (IFNULL(transactional_account.transactional_balance, 0) - IFNULL(loan_account.loan_balance, 0)) AS net_position
FROM CLIENT c
         LEFT JOIN
     (SELECT ca.client_id,
             SUM(ca.display_balance) AS loan_balance
      FROM CLIENT_ACCOUNT ca
               JOIN
           ACCOUNT_TYPE at
      ON ca.account_type_code = at.account_type_code
      WHERE
          at.account_type_code = 'HLOAN'
      GROUP BY
          ca.client_id) loan_account ON c.client_id = loan_account.client_id
         LEFT JOIN
     (SELECT ca.client_id,
             SUM(ca.display_balance) AS transactional_balance
      FROM CLIENT_ACCOUNT ca
               JOIN
           ACCOUNT_TYPE at
      ON ca.account_type_code = at.account_type_code
      WHERE
          at.transactional = TRUE
      GROUP BY
          ca.client_id) transactional_account ON c.client_id = transactional_account.client_id;