import { useEffect, useState } from 'react';
 
import { StatusApprove, pendingAccount, userApprovelAndDenied } from '../ServiceApi/ServiceApi';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Button from '@mui/material/Button';
import Navbar from '../component/Navbar/Navbar';
 
export default function UserApprove() {
    const [rows, setRows] = useState([]);

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

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = event => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleEdit = (row)  => {
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


    const columns = [

        { id: 'firstName', label: 'First Name', minWidth: 100 },
        { id: 'emailId', label: 'Email', minWidth: 120 },
        //   { id: 'status', label: 'Status', minWidth: 100 },
        { id: 'street', label: 'Street', minWidth: 100 },
        { id: 'city', label: 'City', minWidth: 100 },
        { id: 'state', label: 'State', minWidth: 100 },
        { id: 'country', label: 'Country', minWidth: 100 },
        {
            id: 'Approved',
            label: 'Approved',
            minWidth: 50,
            align: 'center',
            format: (value, row) => (
                <Button variant="contained" color="primary" onClick={() => handleEdit(row.userId)}>
                    Approved
                </Button>
            ),
            // row.userId
        },
        {
            id: 'Denied',
            label: 'Denied',
            minWidth: 50,
            align: 'center',
            format: (value, row) => (
                <Button variant="contained" color="secondary" onClick={() => handleDelete(row.userId)}>
                    Denied
                </Button>
            ),
        },
    ];

    

    return (
        <div>
            <Navbar />
            <br />
            <br />
            <br />
            <br />
            <br />
            <Paper sx={{ width: '100%', overflow: 'hidden' }}>
                <TableContainer sx={{ maxHeight: 440 }}>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                {columns.map(column => (
                                    <TableCell
                                        key={column.id}
                                        align={column.align}
                                        style={{ minWidth: column.minWidth }}
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
                                        >
                                            {columns.map(column => {
                                                const value = row[column.id];
                                                return (
                                                    <TableCell key={column.id} align={column.align}>
                                                        {column.format ? column.format(value, row) : value}
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
