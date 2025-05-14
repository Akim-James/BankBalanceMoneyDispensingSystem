SELECT c.client_id,
       c.name,
       ca.client_account_number,
       ca.display_balance
FROM CLIENT c
         JOIN
     CLIENT_ACCOUNT ca ON c.client_id = ca.client_id
WHERE ca.display_balance IN (SELECT MAX(ca2.display_balance)
                             FROM CLIENT_ACCOUNT ca2
                                      JOIN ACCOUNT_TYPE cat ON cat.account_type_code = ca2.account_type_code
                             WHERE ca2.client_id = c.client_id
                               AND cat.transactional = true);