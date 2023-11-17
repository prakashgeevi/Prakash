import { useEffect, useState } from 'react';
 
import { StatusApprove, pendingAccount, userApprovelAndDenied } from '../../ServiceApi/ServiceApi';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Button from '@mui/material/Button';
import Navbar from '../../component/Navbar/Navbar';
import { Avatar } from '@mui/material';


export default function UserMgmt() {
    const [rows, setRows] = useState([]);


    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = event => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };



    const columns = [
        { id: 'profilePicture', label: 'Picture', maxWidth: 70 },
        { id: 'firstName', label: 'First Name', maxWidth: 70 },
        { id: 'emailId', label: 'Email', maxWidth: 80 },
        //   { id: 'status', label: 'Status', minWidth: 100 },
        { id: 'street', label: 'Street', maxWidth: 70 },
        { id: 'city', label: 'City', maxWidth: 70 },
        { id: 'state', label: 'State', maxWidth: 70 },
        { id: 'country', label: 'Country', maxWidth:70 },
        { id: 'role', label: 'role', maxWidth: 70 },
        { id: 'status', label: 'Status', maxWidth: 70 },
        { id: 'action', label: 'Action', maxWidth: 150 },

    ];

    const handleEdit = (row) => {

        const bodyParameters = {
            userId: row,
            status: "APPROVED"
        };

        StatusApprove(bodyParameters).then(response => {
            getAllUsers();
        })
    };

    const handleDelete = (row) => {

        const bodyParameters = {
            userId: row,
            status: "PENDING"
        };

        StatusApprove(bodyParameters).then(response => {
            getAllUsers();
        })
    };

    function getAllUsers() {
        pendingAccount().then(response => {
            const datas = response.data;
            setRows(datas);
        })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    useEffect(() => {
        getAllUsers();

    }, []);

    return (
        <div>
            <Navbar />
            <br />
            <br />
            <br />
            <br />
            <br />
            <Paper sx={{ width: '98%', padding:'10px'}}>
            <TableContainer sx={{ minHeight: 400, maxHeight: 400, width: '100%', fontFamily:'Siara' }}>
                    <Table stickyHeader aria-label="sticky table" className="min-w-max w-full table-auto " style={{width:'100%'}}>
                      <TableHead style={{ backgroundColor: '#566573', color: 'white', padding: '10px' }}>
                            <TableRow style={{ textAlign: 'center' }}>
                                {columns.map(column => (
                                    <TableCell
                                    class="py-3 px-6 text-center"
                                        key={column.id}
                                        align={column.align}
                                        style={{ width: column.maxWidth, backgroundColor: '#566573', color: 'white', padding: '10px', fontFamily:'Siara' }}
                                    >
                                        {column.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {rows
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map(row => {
                                    return (
                                        <TableRow
                                            hover
                                            role="checkbox"
                                            tabIndex={-1}
                                            key={row.userId}
                                            style={{ textAlign: 'center' }}
                                        >
                                            {columns.map(column => {
                                                const value = row[column.id];
                                                const name = row[column.firstName];
                                                const value1 = column.id;
                                                console.log(value);
                                                return (

                                                    <TableCell key={column.id} align={column.align } style={{ width: column.minWidth, textAlign: 'center' }} class="whitespace-nowrap">
                                                        {
                                                            value1 === "action" ? (
                                                                <>
                                                                    <Button variant="contained" color="primary" onClick={() => handleEdit(row.userId)}>
                                                                        Approve
                                                                    </Button>&nbsp;&nbsp;
                                                                    <Button variant="contained" color="error" onClick={() => handleDelete(row.userId)}>
                                                                        Decline
                                                                    </Button>

                                                                </>
                                                            ) : ( value1 === "profilePicture" ? (
                                                                    value ? (
                                                                    <img height={70} width={70} src={'data:image/png;base64,' +value} style={{borderRadius: '50px'}}/>
                                                                     ) : 
                                                                    (
                                                                        <Avatar src={name} style={{width:'70px', height:'70px', marginLeft:'20px'}}></Avatar>
                                                                    )
                                                                
                                                                           
                                                                        ) : (
                                                                            column.format ? column.format(value, row) : value
                                                                        )
                                                            )
                                                        }
                                                    </TableCell>
                                                );
                                            })}
                                        </TableRow>
                                    );
                                })}
                        </TableBody>

                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[10, 25, 100]}
                    component="div"
                    count={rows.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>
        </div>
    );
}
