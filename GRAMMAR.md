# quick draft

variable : 'save' x 'in' id
variable-types : (int | bool | char | string | list | ...) 

function : 'wrap' 'id' id '--' (...) '--'
  call : x()
lambda : 'wrap' id --' (...) '--'

loops : (for | while | for in)
for : 'for' '(' id  ('<'|'>'|'==') ('++'|'--'|'+='|...) [d] ')' '--' '--'
